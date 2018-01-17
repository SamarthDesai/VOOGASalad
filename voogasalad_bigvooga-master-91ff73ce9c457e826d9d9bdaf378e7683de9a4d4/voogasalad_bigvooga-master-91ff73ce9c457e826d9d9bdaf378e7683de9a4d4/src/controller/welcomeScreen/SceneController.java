package controller.welcomeScreen;

import java.util.HashMap;
import java.util.Map;

import authoring_UI.MainAuthoringGUI;
import controller.player.Debugging;
import gui.welcomescreen.FileSelector;
import gui.welcomescreen.GameSelector;
import gui.welcomescreen.Learn;
import gui.welcomescreen.Settings;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Stores all the scenes accessed by the welcome screen, and allows them to be accessible by a map. This ensures that only one instance of
 * each of these scenes is created, so the states of the scenes can be passed around effectively.
 * @author Samarth Desai
 *
 */
public class SceneController {

	public static final String WELCOME_SCREEN_KEY = "Welcome Screen";
	public static final String GAME_SELECTOR_KEY = "Game Selector";
	public static final String GAME_DISPLAY_KEY = "Game Display";
	public static final String GAME_DEBUG_KEY = "Game Debug";
	public static final String FILE_SELECTOR_KEY = "File Selector";
	public static final String FILE_SELECTOR_KEY_FOR_LOAD = "File Selector For Load";
	public static final String LEARN_KEY = "Learn";
	public static final String SETTINGS_KEY = "Settings";
	
	private Map<String, Scene> sceneMap = new HashMap<String, Scene>();
	private Stage stage;
	private Scene scene;
	private FileSelector fileSelector;
	private FileSelector fileSelectorForLoad;
	
	/**
	 * Initializes all the scenes and puts them in the sceneMap.
	 * 
	 * @param currentStage - The instance of the stage being passed
	 */
	public SceneController(Stage currentStage) {
		stage = currentStage;
		WelcomeScreen welcomeScreen = new WelcomeScreen (stage, this);
		welcomeScreen.createWelcomeScreen();
		scene = welcomeScreen.getScene();
		sceneMap.put(WELCOME_SCREEN_KEY, scene);
		
		GameSelector gameSelector = new GameSelector(stage, this);
		gameSelector.createGameSelector();
		scene = gameSelector.getScene();
		sceneMap.put(GAME_SELECTOR_KEY, scene);
		
		fileSelector = new FileSelector(stage, this);
		fileSelector.createFileSelector();
		scene = fileSelector.getScene();
		sceneMap.put(FILE_SELECTOR_KEY, scene);
		
		fileSelectorForLoad = new FileSelector(stage, this);
		fileSelector.createFileSelector();
		scene = fileSelector.getScene();
		sceneMap.put(FILE_SELECTOR_KEY_FOR_LOAD, scene);
		
		Learn learn = new Learn(stage, this);
		learn.createLearn();
		scene = learn.getScene();
		sceneMap.put(LEARN_KEY, scene);

		Settings settings = new Settings(stage, this);
		settings.createSettings();
		scene = settings.getScene();
		sceneMap.put(SETTINGS_KEY, scene);
		
		
	}
	
	/**
	 * Changes and sets the scene.
	 * 
	 * @param key - The key that extracts the correct scene from the map to use
	 */
	public void switchScene (String key) {
		stage.setScene(sceneMap.get(key));
	}
	
	public void saveWorlds() {
		fileSelector.saveWorlds();
	}
	
	public void exportToEngine() {
		fileSelector.exportToEngine();
	}

	public void importWorlds(String name) {
		fileSelector.importWorlds(name);
	}
}