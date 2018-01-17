package authoring_UI;

import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.CutScene.SuperlayerSequence;
import authoring.DialogSprite.AuthoringDialogSequence;
import authoring_data.SpriteObjectGridToEngineController;
import controller.authoring.AuthoringController;
import controller.welcomeScreen.SceneController;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Creates the authoring environment for VOOGA users. This class initializes the controller for the authoring views and creates a new stage
 * with the default scene being the map editor.
 * 
 * 
 * @author Samarth Desai, Sam Slack, and Dara Buggay
 *
 */
public class MainAuthoringGUI {
	public static final int AUTHORING_WIDTH = 1400;
	public static final String AUTHORING_CSS = "Authoring.css";
	private static final String BORDERPANE_ID = "borderpane";
	private Stage stage;
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootPane;
	private ToolBar toolBar;
	private MenuButton fileOptions;
	private MenuButton settings;
	private Pane authoringPane;
	private AuthoringController authoringController;
	private static final String TEMP_PROJECT_NAME = "TestProject";
	private String myProjectName;
	private GameDataHandler myGDH;
	private SpriteObjectGridToEngineController myEngineExporter;
	private String projectToImportTo;
	private AuthoringEnvironmentManager myAEM;

	public MainAuthoringGUI(Stage currentStage, SceneController currentSceneController, String projectName) {
		myProjectName = projectName;
		stage = currentStage;
		rootPane = new BorderPane();
		rootPane.setId(BORDERPANE_ID);
		rootPane.setPrefWidth(AUTHORING_WIDTH);
		scene = new Scene(rootPane, AUTHORING_WIDTH, WelcomeScreen.HEIGHT);
		scene.getStylesheets().add(MainAuthoringGUI.class.getResource(AUTHORING_CSS).toExternalForm());
		sceneController = currentSceneController;
	}
	
	public void setProjectToImportTo(String projectName) {
		projectToImportTo = projectName;
	}

	public void createAuthoringGUI() {
		myGDH = new GameDataHandler(stage, myProjectName);
		
		toolBar = new Toolbar(stage, sceneController, myGDH);
		rootPane.setTop(toolBar);

		authoringPane = new Pane();
		
		myGDH = new GameDataHandler(stage, myProjectName);
		myAEM = new AuthoringEnvironmentManager(myGDH);
		myEngineExporter = new SpriteObjectGridToEngineController(myGDH);
		
		authoringController = new AuthoringController(scene, stage, authoringPane, myAEM);

		ViewSideBar sideBar = new ViewSideBar(authoringController);
		authoringController.switchView(AuthoringController.MAP_EDITOR_KEY, sideBar);

		rootPane.setCenter(authoringPane);
	}

	public Scene getScene() {
		return scene;
	}
	
	public void exportToEngine(){
		for (SuperlayerSequence SS: myAEM.getDialogSpriteController().getAllSuperlayerSequences()){
			myEngineExporter.addAuthoringDialogSequenceToGameObjectFactory((AuthoringDialogSequence)SS);
		}
		List<DraggableGrid> allWorlds = authoringController.getExistingWorlds();
		allWorlds.forEach(DG->{
		myEngineExporter.createLayerAndAddToEngine(DG);
	});
		myEngineExporter.saveEngine();
	}
	
	public void importWorlds(String projectName) {
		List<DraggableGrid> updateGrids = myGDH.loadWorldsFromWorldDirectory(projectName);
		authoringController.importGrids(updateGrids);
	}

	public void saveWorlds() {
		System.out.println("GDH is saving... " + authoringController.getExistingWorlds().size() + " worlds");
		myGDH.saveWorlds(authoringController.getExistingWorlds());
	}
}