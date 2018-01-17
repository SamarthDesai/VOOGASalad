package authoring_UI;

import controller.authoring.AuthoringController;
import javafx.animation.TranslateTransition;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tools.DisplayLanguage;

/**
 * Creates the sliding side bar that allows the user to toggle between the views, which are the Map Editor, Sprite Creator, Custom Panels, Cutscenes, Dialogues,
 * Heads Up Display, Inventory, and Menu Creator. The applications of these views are described in detail in the AuthoringController class documentation.
 * 
 * @author Samarth Desai
 *
 */
public class ViewSideBar extends VBox {

	private static final String ID_NAME = "sideBar";
	private static final int SIDE_BAR_WIDTH = 200;
	public static final int VIEW_MENU_HIDDEN_WIDTH = 10;
	private static final int TRANSLATION_X_COORD = -SIDE_BAR_WIDTH + VIEW_MENU_HIDDEN_WIDTH;
	private static final int TRANSLATION_DURATION_MILLIS = 500;
	private static final int TRANSLATION_RATE = 1;
	private static final String MAP_EDITOR = "MapEditor";
	private static final String SPRITE_CREATOR = "SpriteCreator";
	private static final String INVENTORY_CREATOR = "InventoryCreator";
	private static final String CUTSCENES = "Cutscenes";
	private static final String DIALOGUE = "Dialogue";
	private static final String INVENTORY = "Inventory";
	private static final String HUD = "HUD";
	private static final String MENU_CREATOR = "MenuCreator";
	
	private AuthoringController authoringController;
	private TranslateTransition thisTranslation;
	private ToggleGroup viewGroup = new ToggleGroup();
	
	public ViewSideBar (AuthoringController currentAuthoringController) {
		
		authoringController = currentAuthoringController;
		
		this.setId(ID_NAME);
	    this.setPrefWidth(SIDE_BAR_WIDTH);

	    RadioButton mapButton = createViewButton(MAP_EDITOR, AuthoringController.MAP_EDITOR_KEY);
	    mapButton.setSelected(true);
	    
	    this.getChildren().addAll(
	    		mapButton,
	    		createViewButton(SPRITE_CREATOR, AuthoringController.SPRITE_CREATOR_KEY),
	    		createViewButton(INVENTORY_CREATOR, AuthoringController.INVENTORY_CREATOR_KEY),
	    		createViewButton(CUTSCENES, AuthoringController.CUTSCENES_KEY),
	    		createViewButton(DIALOGUE, AuthoringController.DIALOGUE_KEY),
	    		createViewButton(INVENTORY, AuthoringController.INVENTORY_KEY),
	    		createViewButton(HUD, AuthoringController.HUD_KEY),
	    		createViewButton(MENU_CREATOR, AuthoringController.MENU_CREATOR_KEY)
	    		);

	    this.getStylesheets().add(ViewSideBar.class.getResource("ViewMenu.css").toExternalForm());
	    this.setTranslateX(TRANSLATION_X_COORD);
	    thisTranslation = new TranslateTransition(Duration.millis(TRANSLATION_DURATION_MILLIS), this);

	    thisTranslation.setFromX(TRANSLATION_X_COORD);
	    thisTranslation.setToX(0);

	    this.setOnMouseEntered(e -> translate(TRANSLATION_RATE));
	    this.setOnMouseExited(e -> translate(-TRANSLATION_RATE));
	    
	}
	
	private RadioButton createViewButton(String view, String viewKey) {
		RadioButton viewButton = new RadioButton(view);
		viewButton.getStyleClass().remove("radio-button");
		viewButton.getStyleClass().add("toggle-button");
		viewButton.setToggleGroup(viewGroup);
		viewButton.textProperty().bind(DisplayLanguage.createStringBinding(view));
		viewButton.setOnAction(e -> authoringController.switchView(viewKey, this));
		return viewButton;
	}
	
	private void translate (int transRate) {
    	thisTranslation.setRate(transRate);
        thisTranslation.play();
    }
}

