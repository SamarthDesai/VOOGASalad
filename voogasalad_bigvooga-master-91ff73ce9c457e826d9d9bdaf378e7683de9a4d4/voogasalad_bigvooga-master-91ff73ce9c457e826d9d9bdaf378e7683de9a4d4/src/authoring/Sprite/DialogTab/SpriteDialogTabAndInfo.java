package authoring.Sprite.DialogTab;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.CutScene.SuperlayerSequence;
import authoring.CutScene.SuperlayerThumbnail;
import authoring.DialogSprite.SuperlayerAuthoringImage;
import authoring.DialogSprite.AuthoringDialogSequence;
import authoring.DialogSprite.DialogThumbnail;
import authoring.Sprite.AbstractSpriteObject;
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

public class SpriteDialogTabAndInfo {

	private VBox containerVBox;
	private SpriteScrollView myTabPane;
	// private ArrayList<AbstractSpriteObject> myInventory;
	private AbstractSpriteObject myASO;
	private final static double MENU_WIDTH = 400;
	private final static double MENU_HEIGHT = 500;
	private Consumer<Pane> itemOnClickAction;
	private Consumer buttonAction;
	private String buttonText;
	private AuthoringEnvironmentManager myAEM;

	private Set<SuperlayerSequence> temporaryDialogSequences;

	public SpriteDialogTabAndInfo(AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		createBoundingScrollPane();
		initialize();
	}

	SpriteDialogTabAndInfo(AbstractSpriteObject ASO, AuthoringEnvironmentManager AEM) {
		this(AEM);
		setSpriteObject(ASO);
		remakeContainingVBoxFromNewInventory();
	}

	private void initialize() {
		containerVBox = new VBox(5);
		containerVBox.setPrefWidth(495);
		containerVBox.setAlignment(Pos.TOP_CENTER);

		// myInventory = new ArrayList<AbstractSpriteObject>();
		setTemporaryInfo();
		// removedInventory = new ArrayList<AbstractSpriteObject>();
		this.setClickEvent(click -> {
			// Nothing by default
		});
		this.setButton("Add to dialogue", action -> {
			List<SuperlayerSequence> newInventory = triggerPopUpOfInventoryToChoose();
			temporaryDialogSequences.addAll(newInventory);
			newInventory.forEach(sprite -> {
				addInventory(sprite);
			});
		});
		containerVBox.getChildren().addAll(myTabPane, makeAddInventoryButton());
	}

	protected void setTemporaryInfo() {
		temporaryDialogSequences = new HashSet<SuperlayerSequence>();
	}

	public void setSpriteObjectAndUpdate(AbstractSpriteObject ASO) {
		System.out.println("Setting sprite" + ASO);
		setSpriteObject(ASO);
		remakeContainingVBoxFromNewInventory();
	}

	public void setSpriteObject(AbstractSpriteObject ASO) {
		myASO = ASO;
		setDialogSequences(ASO.getDialogSequences());
	}

	public void reset() {
		myASO = null;
		resetScrollPane();
	}

	public void addInventory(SuperlayerSequence ASO) {
		SuperlayerThumbnail ST = new SuperlayerThumbnail(ASO, true);
		VBox paneBox = new VBox();
		paneBox.getChildren().addAll(ST, new Separator());
		ST.addSideButton("Remove");
		ST.setSideButtonRunnable(() -> {
			removeFromInventory(ST, paneBox);
		});
		ST.setOnMouseClicked(event -> {
			itemOnClickAction.accept(ST);
		});
		myTabPane.addToVBoxNoSeparator(paneBox);
	}

	private void remakeContainingVBoxFromNewInventory() {
		resetScrollPane();
		temporaryDialogSequences.forEach(sprite -> {
			addInventory(sprite);
		});
	}

	private void setDialogSequences(List<AuthoringDialogSequence> newInventory) {
		temporaryDialogSequences = new HashSet<SuperlayerSequence>();
		if (newInventory != null) {
			temporaryDialogSequences.addAll(newInventory);
		}
	}

	private void removeFromInventory(SuperlayerThumbnail ST, VBox paneBox) {
		myTabPane.removeFromVBox(paneBox);
		temporaryDialogSequences.remove(ST.getSuperlayerSequence());
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

	private List<SuperlayerSequence> triggerPopUpOfInventoryToChoose() {

		TabPane tp = new TabPane();
		tp.setSide(Side.TOP);

		List<Pane> dSeqs = myAEM.getDialogSpriteController().getAllSpritesAsThumbnails();

		Tab tab = new Tab("Dialogs");
		tab.setClosable(false);
		// dSeqs.forEach((dseq) -> {
		// key = dseq.getName();
		// Tab tab = new Tab(key);
		// tab.setClosable(false);
		SpriteScrollView SSV = new SpriteScrollView("Dialog Sequences");
		SSV.addToVBox(dSeqs);
		SSV.setChildOnClickAction(pane -> {
			if (pane instanceof SuperlayerThumbnail) {
				SuperlayerThumbnail ST = (SuperlayerThumbnail) pane;
				ST.isClicked(!ST.isClicked());
				if (ST.isClicked()) {
					SSV.addToSpriteList(ST.getSuperlayerSequence().getImage());
				} else {
					SSV.removeFromSpriteList(ST.getSuperlayerSequence().getImage());
				}

			}
		});
		tab.setContent(SSV);

		// });
		tp.getTabs().add(tab);
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		Node parent = myTabPane.getParent();
		Scene s = parent.getScene();
		while (s == null) {
			parent = parent.getParent();
			s = parent.getScene();
		}
		dialog.initOwner(s.getWindow());
		Scene dialogScene = new Scene(tp, 525, 400);
//		dialogScene.getStylesheets().add(SpriteDialogTabAndInfo.class.getResource("Inventory.css").toExternalForm());
		dialog.setScene(dialogScene);

		dialog.showAndWait();
		List<SuperlayerSequence> ret = new ArrayList<SuperlayerSequence>();
		tp.getTabs().forEach((tab1) -> {
			SpriteScrollView SSV1 = (SpriteScrollView) tab1.getContent();
			SSV1.getSpriteList().forEach(dialogIm -> {
				ret.add(((SuperlayerAuthoringImage) dialogIm).getSuperlayerSequence());
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

	public void apply() {
		// myInventory.addAll(temporaryInventory);
		Set<AuthoringDialogSequence> DS = new HashSet<AuthoringDialogSequence>();
		temporaryDialogSequences.forEach(dialog->{
			DS.add((AuthoringDialogSequence)dialog.clone());
		});
		myASO.setDialogSequences(DS);
	}
}