package gui.player;

import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.player.Debugging;
import controller.player.PlayerManager;
import controller.welcomeScreen.SceneController;
import engine.EngineController;
import engine.VoogaException;
import engine.sprite.Displayable;
import engine.sprite.DisplayableImage;
import engine.sprite.DisplayableText;
import engine.sprite.Positionable;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class serves as the player that displays any game the user created in
 * authoring. When a user loads up a game from the game selector, GameDisplay is
 * initialized and acts as the primary medium for interaction and viewing of the
 * game for the user. Any user inputs during a game are sent from this
 * GameDisplay to the game engine through the PlayerManager controller.
 * 
 * @author Samarth Desai
 *
 */
public class GameDisplay {

	private Stage stage;
	private Scene scene;
	private BorderPane rootPane;
	private Pane gamePane;
	private SceneController sceneController;
	private PlayerManager playerManager;
	private GameDataHandler gameDataHandler;

	/**
	 * Primarily sets up the stage, root pane, and scene for the GameDisplay view.
	 * 
	 * @param currentStage
	 *            - The stage instance
	 * @param currentSceneController
	 *            - The scene controller instance
	 */
	public GameDisplay(Stage currentStage, SceneController currentSceneController) {
		stage = currentStage;
		rootPane = new BorderPane();
		gamePane = new Pane();
		rootPane.setCenter(gamePane);
		rootPane.setBackground(new Background(new BackgroundFill[] { new BackgroundFill(Color.WHITE, null, null) }));
		sceneController = currentSceneController;
		scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
	}

	/**
	 * Sets the key and mouse events that are inputs in the player.
	 */
	public void createGameDisplay() {
		scene.setOnKeyPressed(e -> playerManager.setKeyPressed(e.getCode()));
		scene.setOnKeyReleased(e -> playerManager.setKeyReleased(e.getCode()));
		scene.setOnKeyTyped(e -> playerManager.setCharTyped(e.getCharacter()));
		gamePane.setOnMousePressed(e -> playerManager.setPrimaryButtonDown(e.getX(), e.getY()));
		gamePane.setOnMouseReleased(e -> playerManager.setPrimaryButtonUp(e.getX(), e.getY()));
		gamePane.setOnMouseMoved(e -> playerManager.setMouseXY(e.getX(), e.getY()));

		createBack();
	}

	/**
	 * Creates a back button that sends the user out of the player and into the
	 * welcome screen.
	 */
	private void createBack() {
		Button back = new Button("Back");
		back.setOnMouseClicked(e -> exitToMenu());
		rootPane.setTop(back);
	}

	/**
	 * Passes the PlayerManager into GameDisplay.
	 * 
	 * @param currentPlayerManager
	 *            - The instance of PlayerManager
	 */
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;

	}

	/**
	 * Passes the GameDataHandler into GameDisplay.
	 * 
	 * @param currentGameDataHandler
	 *            - The instance of GameDataHandler
	 */
	public void setDataHandler(GameDataHandler currentGameDataHandler) {
		gameDataHandler = currentGameDataHandler;

	}

	/**
	 * Takes in the updated objects from PlayerManager and displays them 
	 * using the visitor pattern.
	 * 
	 * @param imageData
	 *            - The list of objects for the player to display
	 */
	public void setUpdatedDisplayables(List<Displayable> images) {
		gamePane.getChildren().clear();

		for (Displayable d : images) {
			d.visit(this);
		}
	}

	public void displayImage(DisplayableImage image) {
		ImageView gameImage = null;
		try {
			gameImage = new ImageView(gameDataHandler.getImage(image.getFileName()));
		} catch (VoogaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			return;
		}
		gameImage.setFitWidth(image.getWidth());
		gameImage.setFitHeight(image.getHeight());
		gameImage.setRotate(image.getHeading());
		gameImage.setX(image.getX() - image.getWidth() / 2);
		gameImage.setY(image.getY() - image.getHeight() / 2);
		gamePane.getChildren().add(gameImage);
	}

	public void displayText(DisplayableText displayableText) {
		Text text = new Text(displayableText.getText());
		text.setWrappingWidth(displayableText.getWidth());
		text.setRotate(displayableText.getHeading());
		text.setFont(new Font(displayableText.getFont(), displayableText.getFontSize()));
		String c = displayableText.getColor();
		String[] str = c.split("\\(|,|\\)");
		text.setStroke(Color.rgb(Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3])));
		HBox box = new HBox(text);
		Group g = new Group(box);
		g.applyCss();
		g.layout();
		g.setLayoutX(displayableText.getX() - box.getWidth() / 2);
		g.setLayoutY(displayableText.getY() - box.getHeight() / 2);
		gamePane.getChildren().add(g);
	}

	public void drawRectangle(Positionable pos, String color) {
		Rectangle rect = new Rectangle(pos.getX() - pos.getWidth() / 2, pos.getY() - pos.getHeight() / 2,
				pos.getWidth(), pos.getHeight());
		rect.setRotate(pos.getHeading());
		rect.setFill(Color.web(color));
		gamePane.getChildren().add(rect);
	}
	
	/**
	 * Gets the scene for initialization in SceneController. 
	 * 
	 * @return the game display scene
	 */
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene newScene) {
		scene = newScene;
		stage.setScene(scene);
	}

	public void debugMenu(EngineController controls) {
		Debugging debug = new Debugging(this, scene, controls);
		controls.stop();
		scene = debug.createFrame(controls.getCurrentWorld());
		stage.setScene(scene);
	}

	public void exitToMenu() {
		sceneController.switchScene(SceneController.GAME_SELECTOR_KEY);
		playerManager.stop();
	}
}