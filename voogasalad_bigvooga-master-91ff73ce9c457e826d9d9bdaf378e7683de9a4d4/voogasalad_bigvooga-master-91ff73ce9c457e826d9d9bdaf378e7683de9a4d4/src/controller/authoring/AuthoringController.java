package controller.authoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.AuthoringEnvironmentManager;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.ViewSideBar;
import authoring_UI.HUD.HUDManager;
import authoring_UI.Inventory.InventoryManager;
import authoring_UI.Menu.MenuManager;
import authoring_UI.SpriteCreatorTab.SpriteCreatorManagerSlack;
import authoring_UI.cutscene.CutsceneManager;
import authoring_UI.dialogue.DialogueManager;
import engine.utilities.data.GameDataHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Creates the controller that switches between the different views in the authoring environment. These views are currently the Map Editor, Sprite Creator,
 * Custom Panels, Cutscenes, Dialogues, Heads Up Display, Inventory, and Menu Creator. The Map Editor is the default view,
 * which is where the user builds the levels and sprite interactions for the game. The Sprite Creator allows users to create their
 * own sprite templates with custom parameters. Custom Panels allows users to make highly personalized panels for unique cases, such as
 * custom mini-games and other notification-oriented panels for a game. Cutscenes allow users to make full-screen panels that support
 * text and image addition, as well as transition actions to progress a sequence of cutscenes. Useful applications include an in-game splash screen,
 * a game over screen, world transitions, etc. Dialogues allow users to create custom sequences of dialogue to apply to a sprite and then display upon
 * any specified action-condition pairs. Heads Up Display allows a user to create a template for what the game HUD will look like, with support for a
 * background color/image, and any sprites to be dragged onto the HUD. Modifying the elements of the HUD involve conditions and actions that will allow
 * the sprite objects added to the HUD to be changed during the game (i.e. a sprite object that is a horizontal bar can represent health and be added to the HUD, and
 * its pixel width can be decreased or increased through a condition-action pair; this behavior for the horizontal bar effectively mirrors the health decreasing or increasing.
 * Inventory allows users to create a template for any inventory occurrences in the game, which will appear as a grid inside of a box in the game.
 * Finally, the Menu Creator allows users to create a template for the main menu that will appear in the game by setting a background and adding appropriate
 * sprite objects to represent different actions (like saving, exiting the game, etc.).
 * 
 * @author Samarth Desai, Dara Buggay
 *
 */
public class AuthoringController {
	
	public static final String MAP_EDITOR_KEY = "Map Editor";
	public static final String SPRITE_CREATOR_KEY = "Sprite Creator";
	public static final String INVENTORY_CREATOR_KEY = "Inventory Creator";
	public static final String CUSTOM_PANEL_KEY = "Custom Panel";
	public static final String CUTSCENES_KEY = "Cutscenes";
	public static final String DIALOGUE_KEY = "Dialogue";
	public static final String HUD_KEY = "HUD";
	public static final String INVENTORY_KEY = "Inventory";
	public static final String MENU_CREATOR_KEY = "Menu Creator";

	private ObjectProperty<MapManager> activeManagerProperty;
	private Map<String, MapManager> viewMapKeysToManager = new HashMap<String, MapManager>();
	private Map<String, Pane> viewMap = new HashMap<String, Pane>();
	private Pane authoringPane;
	private Scene scene;
	private Pane view;
	private MapManager mapManager;
	private SpriteCreatorManagerSlack mySCM;
	private SpriteCreatorManagerSlack myInventorySCM;
	private GameDataHandler GDH;
	private AuthoringEnvironmentManager myAEM;

	public AuthoringController(Scene currentScene, Stage currentStage, Pane currentAuthoringPane, AuthoringEnvironmentManager AEM) {
		scene = currentScene;
		authoringPane = currentAuthoringPane;
		myAEM = AEM;
		GDH = myAEM.getGameDataHandler();
		activeManagerProperty = new SimpleObjectProperty<MapManager>();
		activeManagerProperty.addListener((change, previousManager, newManager) -> {
			if (previousManager != null) {
				previousManager.gridIsNotShowing();
			}
			if (newManager != null) {
				newManager.gridIsShowing();
			}
		});

		
		mapManager = new MapManager(AEM, scene);
		viewMap.put(MAP_EDITOR_KEY, mapManager.getPane());
		viewMapKeysToManager.put(MAP_EDITOR_KEY, mapManager);

		mySCM = new SpriteCreatorManagerSlack(AEM, scene, "SpriteObject");
		viewMap.put(SPRITE_CREATOR_KEY, mySCM.getPane());
		
		myInventorySCM = new SpriteCreatorManagerSlack(AEM, scene, "InventoryObject");
		viewMap.put(INVENTORY_CREATOR_KEY, myInventorySCM.getPane());

		CutsceneManager cm = new CutsceneManager(AEM);
		cm.addCutsceneListener(mapManager.getCutscenesTab());
		viewMap.put(CUTSCENES_KEY, cm.getPane());

		DialogueManager dm = new DialogueManager(AEM);
		dm.addDialogueListener(mapManager.getDialoguesTab());
		viewMap.put(DIALOGUE_KEY, dm.getPane());
		
		InventoryManager inventoryManager = new InventoryManager(AEM);
		viewMap.put(INVENTORY_KEY, inventoryManager.getPane());

		HUDManager hudManager = new HUDManager(AEM, scene);
		viewMap.put(HUD_KEY, hudManager.getPane());
		viewMapKeysToManager.put(HUD_KEY, hudManager);

		MenuManager menuManager = new MenuManager(AEM, scene);
		viewMap.put(MENU_CREATOR_KEY, menuManager.getPane());
		viewMapKeysToManager.put(MENU_CREATOR_KEY, menuManager);
	}

	/**
	 * Changes and sets the authoring view.
	 * 
	 * @param key
	 *            - The key that extracts the correct view from the viewmap to use
	 */
	public void switchView(String key, ViewSideBar currentSideBar) {
		authoringPane.getChildren().removeAll(view, currentSideBar);
		view = viewMap.get(key);
		if (this.viewMapKeysToManager.containsKey(key)) {
			this.activeManagerProperty.set(viewMapKeysToManager.get(key));
		} else {
			this.activeManagerProperty.set(null);
		}
		authoringPane.getChildren().addAll(view, currentSideBar);
	}

	public List<DraggableGrid> getExistingWorlds() {
		return mapManager.getAllWorlds();
	}
	
	public void importGrids(List<DraggableGrid> importedWorlds) {
		mapManager.addImportedWorlds(importedWorlds);
	}
}