package authoring.SpritePanels;

import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import authoring.AuthoringEnvironmentManager;
import authoring.ObjectManagers.SpriteManagers.SpriteSet;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.DraggableGrid;
import authoring_UI.SpriteGridHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Class that handles creating a user sprite and making all the sprite tabs that are displayed upon sprite selection, including inventory, dialogue, cutscenes
 * 
 * @author Sam Slack, Dara Buggay
 *
 */
public class GameElementSelector extends TabPane implements Observer {
	
	protected static final String SPRITES = "Sprites";
	protected static final String DIALOGUES = "Dialogues";
	protected static final String CUTSCENES = "Cutscenes";
	protected static final String DEFAULT = "Default";
	protected static final String USER = "User";
	protected static final String IMPORTED = "Imported";
	protected static final String IMPORTEDINVENTORY = "Imported Inventory";
	protected static final String INVENTORY = "Inventory";
	
	protected DraggableGrid myGrid;
	private final int NUM_COLUMNS = 10;
	protected AuthoringEnvironmentManager myAEM;
	protected SpriteGridHandler mySpriteGridHandler;
	private Tab dialoguesTab;
	protected String myType;
	private Tab cutscenesTab;
	
	protected GameElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM){
		this(spriteGridHandler, AEM, "");	}

	protected GameElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM, String type) {
		myType = type;
		myAEM = AEM;
		mySpriteGridHandler = spriteGridHandler;
		this.setPrefHeight(280);
		createSpriteTabs();
	}

	/**
	 * creates new user sprite
	 * @author Samuel
	 * @param sp
	 */

	public void createUserSprite(Object spObj) {
		if (!(spObj instanceof SpriteObject)) {
			throw new RuntimeException("Its not a sprite");
		}
		SpriteObject sp = (SpriteObject) spObj;
		try {
			this.myAEM.addUserSprite(sp);
		} catch (Exception e) {
			displayErrorMessage(e);
			e.printStackTrace();
		}
	}
	
	private void displayErrorMessage(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(e.getMessage());
		alert.setContentText("Please give sprite an image");

		alert.showAndWait();
	}


	protected void createSpriteTabs() {
		TabPane spritesTabPane = new TabPane();
		TabPane dialoguesTabPane = new TabPane();
		TabPane cutscenesTabPane = new TabPane();
		TabPane inventoryTabPane = new TabPane();
		Tab defaultSpriteTab = createSubTab(DEFAULT, myAEM.getDefaultSpriteController());
		Tab userSpriteTab = createSubTab(USER, myAEM.getCustomSpriteController());
		Tab importedSpriteTab = createSubTab(IMPORTED, myAEM.getImportedSpriteController());
		Tab inventorySpriteTab = createSubTab(INVENTORY, myAEM.getInventoryController());
		Tab importedInventorySpriteTab = createSubTab(IMPORTEDINVENTORY, myAEM.getImportedInventorySpriteController());
		spritesTabPane.getTabs().addAll(defaultSpriteTab, userSpriteTab, importedSpriteTab);
		spritesTabPane.setSide(Side.RIGHT);
		
		inventoryTabPane.setSide(Side.RIGHT);
		inventoryTabPane.getTabs().addAll(inventorySpriteTab, importedInventorySpriteTab);
		
		Tab spritesTab = createElementTab(SPRITES, spritesTabPane);
		spritesTab.setClosable(false);
		
		dialoguesTab = createElementTab(DIALOGUES, dialoguesTabPane);
		dialoguesTab.setClosable(false);
		
		cutscenesTab = createElementTab(CUTSCENES, cutscenesTabPane);
		cutscenesTab.setClosable(false);
		
		Tab inventoryTab = createElementTab(INVENTORY, inventoryTabPane);
		inventoryTab.setClosable(false);
		this.getTabs().addAll(spritesTab, inventoryTab);
		
		this.setSide(Side.TOP);
	}
	
	protected Tab createSubTab(String tabName, SpriteSet controller) {
		Tab subTab = new Tab();
		subTab.setText(tabName);
		subTab.setContent(makeCategoryTabPane(controller));
		subTab.setOnSelectionChanged((event)->{
			if (subTab.isSelected()){
				subTab.setContent(makeCategoryTabPane(controller));
			}
		});
		subTab.setClosable(false);
		return subTab;
	}
	
	private TabPane makeCategoryTabPane(SpriteSet controller){
		TabPane categoryTabPane = new TabPane();
		categoryTabPane.setSide(Side.LEFT);
		for (Entry<String, List<AbstractSpriteObject>> cat: controller.getAllSpritesAsMap().entrySet()){
			Tab catTab = new Tab();
			catTab.setClosable(false);
			catTab.setText(cat.getKey());
			catTab.setContent(makeGrid(cat.getValue()));
			catTab.setOnSelectionChanged(event->{
				if (catTab.isSelected()){
					catTab.setContent(makeGrid(cat.getValue()));
				}
			});
			categoryTabPane.getTabs().add(catTab);
		}
		return categoryTabPane;
	}
	
	private Tab createElementTab(String tabName, TabPane tabPane) {
		Tab elementTab = new Tab();
		elementTab.setText(tabName);
		elementTab.setContent(tabPane);
		elementTab.setClosable(false);
		return elementTab;
	}

	private ScrollPane makeGrid(List<AbstractSpriteObject> sprites) {
		GridPane gp = new GridPane();
		int totalRows = (int) Math.ceil(sprites.size()/10);
		int DEFAULT_MIN_ROWS = 15;
		totalRows = (totalRows<DEFAULT_MIN_ROWS) ? DEFAULT_MIN_ROWS : totalRows;
		int counter =0;
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < 10; j++) {				
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(
						new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED,
						CornerRadii.EMPTY, BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				if (counter<sprites.size()) {
					AbstractSpriteObject toPopulate = sprites.get(counter);
					System.out.println("Adding " + toPopulate.getImageURL());
					addSpriteGridHandlerFunctionality(toPopulate);
					sp.getChildren().add(toPopulate);
				counter++;
				gp.add(sp, j, i);
			}
		}
	}

		ScrollPane SP = new ScrollPane(gp);
		return SP;
	}
	
	protected void addSpriteGridHandlerFunctionality(AbstractSpriteObject ASO){
		mySpriteGridHandler.addSpriteDrag(ASO);
		mySpriteGridHandler.addSpriteMouseClick(ASO);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		createUserSprite(arg);
	}
	
	public Tab getDialoguesTab() {
		return dialoguesTab;
	}
	
	public Tab getCutscenesTab() {
		return cutscenesTab;
	}
}