package authoring.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.Sprite.Parameters.FEParameterName;
import authoring_UI.SpriteScrollView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SpriteTagTabAndInfo {
	
	private Set<String> temporaryTags;
	private VBox containerVBox;
	private VBox interiorVBox;
	private AbstractSpriteObject myASO;
	private AuthoringEnvironmentManager myAEM;
	private ScrollPane myScrollPane;
	
	public SpriteTagTabAndInfo() {
		this(new SpriteObject(false));
	}

	SpriteTagTabAndInfo(AbstractSpriteObject SO) {
		initialize();
		setSpriteObject(SO);
	}
	
	private void initialize() {
		containerVBox = new VBox(5);
		containerVBox.setPrefWidth(495);
		temporaryTags = new HashSet<String>();
		Button addButton = makeAddTagButton();
		containerVBox.getChildren().addAll(getScrollPane(), addButton);
	}

	public void setSpriteObjectAndUpdate(AbstractSpriteObject ASO) {
		setSpriteObject(ASO);
		remakeContainingVBoxFromNewInventory();
	}

	public void setSpriteObject(AbstractSpriteObject ASO) {
		myASO = ASO;
		setTags(ASO.getTags());
	}

	public void reset() {
		myASO = null;
		resetScrollPane();
	}

	public void addTag(String tag) {
//		SpriteThumbnail ST = new SpriteThumbnail(ASO, true);
		BorderStroke border = new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
		HBox hbox = new HBox();
		
		hbox.setBorder(new Border(border));
		TextArea myValue = new TextArea(tag);
		myValue.setPrefWidth(170);
		myValue.setBorder(new Border(border));
		
		Button button = new Button("Remove");
		button.setOnAction((event)->{
			removeFromInventory(hbox);
			
		});
		hbox.getChildren().addAll(myValue, button);
		hbox.setMaxHeight(3);
		hbox.setSpacing(3);
		
		this.interiorVBox.getChildren().add(hbox);
		;
	}
	

	private void remakeContainingVBoxFromNewInventory() {
		resetScrollPane();
		temporaryTags.forEach(tag -> {
			addTag(tag);
		});
	}

	private void setTags(List<String> newTags) {
		temporaryTags.clear();
		newTags.forEach(string->{
			temporaryTags.add(new String(string));
		});
	
		
	}
	
	private void removeFromInventory(HBox container){
		this.interiorVBox.getChildren().remove(container);
		temporaryTags.remove(((TextArea)container.getChildren().get(0)).getText());
	}

	public VBox getContainingVBox() {
		return containerVBox;
	}
	
	private ScrollPane getScrollPane(){
		if (myScrollPane == null){
			myScrollPane = new ScrollPane();
			myScrollPane.setContent(getInteriorVBox());
		}
		
		return myScrollPane;
	}

	public void resetScrollPane() {
		clearInteriorVBox();
		myScrollPane.setContent(interiorVBox);
	}
	
	private VBox getInteriorVBox(){
		if (interiorVBox == null){
			interiorVBox = new VBox(5);
		}
		return interiorVBox;
	}
	
	private void clearInteriorVBox(){
		interiorVBox.getChildren().clear();
	}

	private Button makeAddTagButton() {
		Button button = new Button("Add tag");
		button.setOnAction(action -> {
			String newTag = triggerPopUpOfTagToMake();
			if (newTag!=null){
			temporaryTags.add(newTag);
			addTag(newTag);
			}	
		});
		return button;
	}

	private String triggerPopUpOfTagToMake() {
		
		TextArea tp = new TextArea();

		
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		Node parent = myScrollPane.getParent();
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
		dialog.setOnCloseRequest(new EventHandler<WindowEvent>(){
			
			public void handle(WindowEvent event){
			String ret = tp.getText();
			;
			if (!temporaryTags.contains(ret)){
					// Nothing let it close
				}
			else {
				;
				tp.setText("That tag already exists");
				((Event) event).consume();
			}
			}
		});
		// dialog.show();
		dialog.showAndWait();
		// dialog.setOnCloseRequest
		String toReturn = tp.getText();
		if (toReturn.equals("")){
			toReturn = null;
		}
		return toReturn;

	}

	
	
	public void apply(){
		myASO.setTags(this.temporaryTags);
	}

}
