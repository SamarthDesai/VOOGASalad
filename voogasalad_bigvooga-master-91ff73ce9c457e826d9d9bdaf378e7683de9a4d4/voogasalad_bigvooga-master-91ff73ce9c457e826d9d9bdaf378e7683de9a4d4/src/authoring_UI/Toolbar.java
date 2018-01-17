package authoring_UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import controller.player.GameController;
import controller.welcomeScreen.SceneController;
import engine.utilities.data.GameDataHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import tools.DisplayLanguage;

/**
 * Acts as the uppermost ToolBar that is accessible from any authoring view. This contains the options for loading and saving any authoring configuration,
 * as well as importing or exporting any VOOGA projects. It also allows the user to exit back to the main menu. Finally, it has two viewers:
 * the element viewer and map viewer. These allow game builders to see a bigger picture of all their sprites and existing maps in a separate window.
 * 
 * @author Samarth Desai and Dara Buggay
 *
 */
public class Toolbar extends ToolBar {
	
	public static final String FILE_SELECTOR = "File Selector";
	private static final String FILE_STRING = "File";
	private static final String LOAD_STRING = "Load";
	private static final String SAVE_STRING = "Save";
	private static final String IMPORT_STRING = "Import";
	private static final String EXPORT_STRING = "Export";
	private static final String TEST_STRING = "Test";
	private static final String EXIT_STRING = "Exit";
	private static final String SETTINGS_STRING = "Settings";
	private static final String GAMES_PATH = "data/UserCreatedGames";
	
	private MenuButton fileOptions;
	private MenuButton settings;
	private SceneController sceneController;
	private Stage myStage;
	private GameDataHandler myGDH;

	/**
	 * Creates the ToolBar and adds the file, viewers, and settings options.
	 * 
	 * @param stage
	 * @param currentSceneController
	 */
	public Toolbar(Stage stage, SceneController currentSceneController, GameDataHandler GDH) {
		myStage = stage;
		sceneController = currentSceneController;
		myGDH = GDH;
		
		createFileOptions();
		createSettings();
		this.getItems().addAll(
				fileOptions,
				settings
				);
	}
	
	/**
	 * Creates the File dropdown button and its 5 options: Load, Save, Import, Export, and Exit. 
	 */
	private void createFileOptions() {
		MenuItem load = new MenuItem();
		load.textProperty().bind(DisplayLanguage.createStringBinding(LOAD_STRING));
		load.setOnAction(e -> this.loadNewGame());
		
		MenuItem save = new MenuItem();
		save.textProperty().bind(DisplayLanguage.createStringBinding(SAVE_STRING));
		save.setOnAction(e -> sceneController.saveWorlds());
		
		Menu importOption = new Menu();
		importOption.textProperty().bind(DisplayLanguage.createStringBinding(IMPORT_STRING));	
		List<MenuItem> importItems = createImportOptions();
		for (MenuItem item : importItems) {
			importOption.getItems().add(item);
		}
		importOption.textProperty().bind(DisplayLanguage.createStringBinding(IMPORT_STRING));
		
		MenuItem export = new MenuItem();
		export.textProperty().bind(DisplayLanguage.createStringBinding(EXPORT_STRING));
		export.setOnAction(e -> sceneController.exportToEngine());
		
		MenuItem test = new MenuItem();
		test.textProperty().bind(DisplayLanguage.createStringBinding(TEST_STRING));
		test.setOnAction(e -> {
			sceneController.exportToEngine();
			Stage gamePlayingStage = new Stage();
			try {
				GameController gameController = new GameController(gamePlayingStage, myGDH.getProjectName(), sceneController, false);
				gamePlayingStage.show();
				gamePlayingStage.setOnCloseRequest(l -> gameController.stop());
			} catch (FileNotFoundException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("The file was not found.");
			}
			
//			sceneController.exportToEngine();
//			File f = new File(new GameDataHandler(myStage).getRoot());
			
			
		});
		
		MenuItem exit = new MenuItem();
		exit.textProperty().bind(DisplayLanguage.createStringBinding(EXIT_STRING));
		exit.setOnAction(e -> sceneController.switchScene(SceneController.WELCOME_SCREEN_KEY));
		
		fileOptions = new MenuButton(FILE_STRING, null, load, save, importOption, export, test, exit);
		fileOptions.textProperty().bind(DisplayLanguage.createStringBinding(FILE_STRING));
	}
	
	/**
	 * Action that sends the user back to the File Selector so they can load a different game into the authoring environment.
	 */
	private void loadNewGame() {
		myStage.close();
		SceneController newScene = new SceneController(new Stage());
		newScene.switchScene(FILE_SELECTOR);
	}
	
	/**
	 * Creates the list of all current games to import to add onto the current project. 
	 * @return the list of games the user can import.
	 */
	private List<MenuItem> createImportOptions() {
		List<MenuItem> importItems = new ArrayList<MenuItem>();

		File f = new File(new GameDataHandler(myStage).getRoot());
		File[] listOfFiles = f.listFiles();
		for (File file: listOfFiles) {
			if(file.getName().equals("resources"))
				continue;
			MenuItem tempItem = new MenuItem(file.getName());
			tempItem.setOnAction(e -> sceneController.importWorlds(file.getName()));
			importItems.add(tempItem);
		}
		return importItems;
	}
	
	/**
	 * Creates the settings option.
	 */
	private void createSettings() {
		settings = new MenuButton (SETTINGS_STRING, null);
		settings.textProperty().bind(DisplayLanguage.createStringBinding(SETTINGS_STRING));
	}
}