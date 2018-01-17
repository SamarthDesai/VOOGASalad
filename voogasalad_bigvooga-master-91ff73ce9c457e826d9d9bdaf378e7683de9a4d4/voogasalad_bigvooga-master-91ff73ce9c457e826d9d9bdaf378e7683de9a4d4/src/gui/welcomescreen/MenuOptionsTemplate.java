package gui.welcomescreen;

import controller.welcomeScreen.SceneController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The superclass that creates the framework for three of the four options in the welcome screen: Play, Learn, and Settings.
 * Specifically, this sets the color scheme, heading, back button, and ScrollPane of information for these three options.
 * 
 * @author Samarth Desai
 *
 */
public class MenuOptionsTemplate {

	public static final int CONTENT_INSET_SIZE = 10;
	public static final String SCROLLPANE_CSS = "ScrollPane.css";
	private static final int BACK_WIDTH = 50;
	private static final int BACK_HEIGHT = 50;
	private static final String BACK_STATIC_PATH = "Back_Static.png";
	private static final String BACK_PATH = "Back.gif";
	private static final Insets CONTENT_PADDING = new Insets(0, CONTENT_INSET_SIZE, CONTENT_INSET_SIZE, CONTENT_INSET_SIZE);
	
	private Stage stage;
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootPane;
	private ScrollPane contentPane;
	
	private Image backStaticImage;
	private Image backImage;
	private ImageView back;
	private Insets contentPadding;	

	/**
	 * 
	 * 
	 * @param currentStage - Stage instance that is being passed
	 * @param currentSceneController - Allows the correct scene to be applied, which is the settings scene
	 */
	public MenuOptionsTemplate(Stage currentStage, SceneController currentSceneController) {
		stage = currentStage;
		sceneController = currentSceneController;
		rootPane = new BorderPane();
		scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
		scene.getStylesheets().add(MenuOptionsTemplate.class.getResource(SCROLLPANE_CSS).toExternalForm());
	}

	public void createOptionScreen(String titleLogoPath, int titleLogoWidth, int titleLogoHeight,
			int topAndBottomPadding, int rightAndLeftPadding, int contentPaneHeight) {
		contentPadding = new Insets(0, rightAndLeftPadding, rightAndLeftPadding, rightAndLeftPadding);
		rootPane.setStyle(WelcomeScreen.SET_BACKGROUND_COLOR + WelcomeScreen.BACKGROUND_COLOR);
		rootPane.setTop(createHeading(titleLogoPath, titleLogoWidth, titleLogoHeight, topAndBottomPadding));
		rootPane.setBottom(createContentBox(contentPaneHeight));
	}
	
	private HBox createHeading(String titleLogoPath, int titleLogoWidth, int titleLogoHeight, int topAndBottomPadding) {
		HBox backButton = createBack(e -> handleBackSelection());
		HBox titleLogo = createLogo(titleLogoPath, titleLogoWidth, titleLogoHeight);
		HBox heading = new HBox((WelcomeScreen.WIDTH/2) - titleLogoWidth/2 - BACK_WIDTH);
		heading.setPadding(new Insets(topAndBottomPadding, 0, topAndBottomPadding, 0));
		heading.getChildren().addAll(backButton, titleLogo);
		return heading;
	}
	
	private HBox createBack(EventHandler<? super MouseEvent> handler) {
		
		backStaticImage = GUITools.createImage(BACK_STATIC_PATH, BACK_WIDTH, BACK_HEIGHT);
		backImage = GUITools.createImage(BACK_PATH, BACK_WIDTH, BACK_HEIGHT);
		back = GUITools.createImageView(backStaticImage);
		handleHover();
		
		HBox backBox = new HBox();
		backBox.getChildren().add(back);
		backBox.setAlignment(Pos.CENTER);
		backBox.setOnMouseClicked(handler);
		return backBox;
		
	}
	
	private void handleHover() {
		back.setOnMouseEntered(e -> backMouseEnter());
		back.setOnMouseExited(e -> backMouseExit());
	}
	
	private void backMouseEnter(){
		back.setImage(backImage);
	}
	
	private void backMouseExit() {
		back.setImage(backStaticImage);
	}
	
	private void handleBackSelection() {
		
		sceneController.switchScene(SceneController.WELCOME_SCREEN_KEY);
		
	}

	private HBox createLogo(String titleLogoPath, int titleLogoWidth, int titleLogoHeight) {

		Image logoImage = GUITools.createImage(titleLogoPath, titleLogoWidth, titleLogoWidth);
		ImageView logo = GUITools.createImageView(logoImage);
		
		HBox logoBox = new HBox();
		logoBox.getChildren().add(logo);
		return logoBox;

	}
	
	private ScrollPane createContentBox(int contentPaneHeight) {		
		contentPane = new ScrollPane();
		contentPane.setPrefWidth(WelcomeScreen.WIDTH);
		contentPane.setPrefHeight(contentPaneHeight);
		contentPane.setStyle(GUITools.styleBox(WelcomeScreen.BORDER_COLOR));
		contentPane.setPadding(contentPadding);
		BorderPane.setMargin(contentPane, CONTENT_PADDING);
		return contentPane;
	}
	
	protected ScrollPane getScrollPane() {
		return contentPane;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public BorderPane getBorderPane() {
		return rootPane;
	}
}
