package authoring.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.Holdable;
import authoring_UI.SpriteScrollView;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpriteInventoryTabAndInfo {

	private VBox containerVBox;
	private SpriteScrollView myTabPane;
//	private ArrayList<AbstractSpriteObject> myInventory;
	private AbstractSpriteObject myASO;
	private final static double MENU_WIDTH = 400;
	private final static double MENU_HEIGHT = 500;
	private Consumer<Pane> itemOnClickAction;
	private Consumer buttonAction;
	private String buttonText;
	private AuthoringEnvironmentManager myAEM;

	private Set<AbstractSpriteObject> temporaryInventory;

	public SpriteInventoryTabAndInfo(AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		createBoundingScrollPane();
		initialize();
	}

	SpriteInventoryTabAndInfo(AbstractSpriteObject ASO, AuthoringEnvironmentManager AEM) {
		this(AEM);
		setSpriteObject(ASO);
		remakeContainingVBoxFromNewInventory();
	}

	private void initialize() {
		containerVBox = new VBox(5);
		containerVBox.setPrefWidth(495);
		containerVBox.setAlignment(Pos.TOP_CENTER);

//		myInventory = new ArrayList<AbstractSpriteObject>();
		setTemporaryInfo();
//		removedInventory = new ArrayList<AbstractSpriteObject>();
		this.setClickEvent(click -> {
			// Nothing by default
		});
		this.setButton("Add to inventory", action -> {
			List<AbstractSpriteObject> newInventory = triggerPopUpOfInventoryToChoose();
			temporaryInventory.addAll(newInventory);
			newInventory.forEach(sprite -> {
				addInventory(sprite);
			});
		});
		containerVBox.getChildren().addAll(myTabPane, makeAddInventoryButton());
	}
	
	protected void setTemporaryInfo(){
		temporaryInventory = new HashSet<AbstractSpriteObject>();
	}
	

	public void setSpriteObjectAndUpdate(AbstractSpriteObject ASO) {
		;
		setSpriteObject(ASO);
		remakeContainingVBoxFromNewInventory();
	}

	public void setSpriteObject(AbstractSpriteObject ASO) {
		myASO = ASO;
		setInventory(ASO.getInventory());
	}

	public void reset() {
		myASO = null;
		resetScrollPane();
	}

	public void addInventory(AbstractSpriteObject ASO) {
		SpriteThumbnail ST = new SpriteThumbnail(ASO, true);
		VBox paneBox = new VBox();
		paneBox.getChildren().addAll(ST, new Separator());
		ST.addSideButton("Remove");
		ST.setSideButtonRunnable(()->{
			removeFromInventory(ST, paneBox);
		});
		ST.setOnMouseClicked(event -> {
			itemOnClickAction.accept(ST);
		});
		myTabPane.addToVBoxNoSeparator(paneBox);
	}
	

	private void remakeContainingVBoxFromNewInventory() {
		resetScrollPane();
		temporaryInventory.forEach(sprite -> {
			addInventory(sprite);
		});
	}

	private void setInventory(List<AbstractSpriteObject> newInventory) {
		temporaryInventory.clear();
		temporaryInventory.addAll(newInventory);
	}
	
	private void removeFromInventory(SpriteThumbnail ST, VBox paneBox){
		myTabPane.removeFromVBox(paneBox);
		temporaryInventory.remove(ST.getSprite());
	}

	private void createBoundingScrollPane() {
		myTabPane = new SpriteScrollView();
	}

	public VBox getContainingVBox() {
		return containerVBox;
	}

	public void resetScrollPane() {
		myTabPane.clearVBox();
	}

	private Button makeAddInventoryButton() {
		Button button = new Button(buttonText);
		button.setOnAction(e -> {
			buttonAction.accept(e);
		});
		return button;
	}

	private List<AbstractSpriteObject> triggerPopUpOfInventoryToChoose() {
		
		TabPane tp = new TabPane();
		tp.setSide(Side.TOP);

		Map<String, List<Pane>> panes = myAEM.getEveryTypeOfSpriteAsThumbnails();
		
		panes.forEach((key, value) -> {
			Tab tab = new Tab(key);
			tab.setClosable(false);
			SpriteScrollView SSV = new SpriteScrollView();
			SSV.addToVBox(value);
			SSV.setChildOnClickAction(pane -> {
				if (pane instanceof SpriteThumbnail) {
					SpriteThumbnail ST = (SpriteThumbnail) pane;
					ST.isClicked(!ST.isClicked());
					if (ST.isClicked()) {
						SSV.addToSpriteList(ST.getSprite());
					} else {
						SSV.removeFromSpriteList(ST.getSprite());
					}
		// SpriteInventoryTabAndInfo dummy = new SpriteInventoryTabAndInfo();
//		SpriteScrollView SSV = new SpriteScrollView();
//		SSV.setChildOnClickAction(pane -> {
//			if (pane instanceof SpriteThumbnail) {
//				SpriteThumbnail ST = (SpriteThumbnail) pane;
//				ST.isClicked(!ST.isClicked());
//				if (ST.isClicked()) {
//					SSV.addToSpriteList(ST.getSprite());
//				} else {
//					SSV.removeFromSpriteList(ST.getSprite());
//				}
//			}
//		});
//		SSV.addToVBox(myAEM.getEveryTypeOfSpriteAsThumbnails());
//		VBox vb = new VBox(10);
				}
			});
			tab.setContent(SSV);
			tp.getTabs().add(tab);
			// ScrollPane SP = new ScrollPane();
			// VBox VB = new VBox(5);
			// value.forEach(pane->);
		});
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		Node parent = myTabPane.getParent();
		Scene s = parent.getScene();
		while (s == null) {
			parent = parent.getParent();
			s = parent.getScene();
		}
		dialog.initOwner(s.getWindow());
		// VBox dialogVbox = new VBox(20);
		// dialogVbox.getChildren().add(new Text("This is a Dialog"));
		Scene dialogScene = new Scene(tp, 525, 400);
		dialogScene.getStylesheets().add(SpriteInventoryTabAndInfo.class.getResource("Inventory.css").toExternalForm());
		dialog.setScene(dialogScene);
		
		// dialog.show();
		dialog.showAndWait();
		// dialog.setOnCloseRequest
		List<AbstractSpriteObject>  ret = new ArrayList<AbstractSpriteObject>();
		tp.getTabs().forEach((tab)->{
			SpriteScrollView SSV = (SpriteScrollView) tab.getContent();
			SSV.getSpriteList().forEach(imageview->{
				ret.add((AbstractSpriteObject)imageview);
			});
			
		});
		return ret;

	}

	public void setClickEvent(Consumer<Pane> consumer) {
		itemOnClickAction = consumer;
	}

	public void setButton(String description, Consumer consumer) {
		buttonText = description;
		buttonAction = consumer;
	}
	
	public void apply(){
//		myInventory.addAll(temporaryInventory);
		myASO.setInventory(temporaryInventory);
	}
}