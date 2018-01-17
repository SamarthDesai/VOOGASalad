package gui.welcomescreen;

import controller.welcomeScreen.SceneController;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates and populates the Learn page, which contains all the instructions for how to use VOOGA.
 * 
 * @author Samarth Desai
 *
 */
public class Learn extends MenuOptionsTemplate {

	/**
	 *The content inset size times four is subtracted from the screen width because the ScrollPane is the entire width of the screen,
	 *minus its two insets on the left and two on the right, which are all the same size. Thus, to properly wrap the text, the subtraction of
	 *the shared inset size times four correctly accounts for all the horizontal padding that exists for the ScrollPane. Then, an additional
	 *four is subtracted because the border width is two, so for both the left and right border that makes four additional pixels.
	 */
	public static final int WRAPPING_WIDTH = WelcomeScreen.WIDTH - CONTENT_INSET_SIZE*4 - 4;
	private static final String CRYSTAL_PATH = "Crystal.gif";
	private static final int CRYSTAL_WIDTH = 125;
	private static final int CRYSTAL_HEIGHT = 125;
	private static final String INSTRUCTIONS_INTRO_HEADING_SIZE = 50 + "pt;";
	private static final String INSTRUCTIONS_CONTENT_HEADING_SIZE = 30 + "pt;";
	private static final String INSTRUCTIONS_BODY_SIZE = 12 + "pt;";
	private static final String INSTRUCTIONS_INTRO_HEADING = "IntroHeading";
	private static final String INSTRUCTIONS_INTRO_BODY = "IntroBody";
	private static final String INSTRUCTIONS_AUTHORING_HEADING = "AuthoringHeading";
	private static final String INSTRUCTIONS_AUTHORING_BODY = "AuthoringBody";
	private static final String INSTRUCTIONS_PLAYER_HEADING = "PlayerHeading";
	private static final String INSTRUCTIONS_PLAYER_BODY = "PlayerBody";
	private static final String INSTRUCTIONS_FAQ_HEADING = "FAQHeading";
	private static final String INSTRUCTIONS_FAQ_BODY = "FAQBody";

	private ScrollPane contentPane = new ScrollPane();
	
	/**
	 * Creates the framework of the Learn page by passing the necessary information to the MenuOptionsTemplate superclass.
	 * 
	 * @param currentStage - Stage instance that is being passed
	 * @param sceneController - Allows the correct scene to be applied, which is the settings scene
	 */
	public Learn(Stage currentStage, SceneController sceneController) {
		super(currentStage, sceneController);
		createOptionScreen(CRYSTAL_PATH, CRYSTAL_WIDTH, CRYSTAL_HEIGHT, 0, MenuOptionsTemplate.CONTENT_INSET_SIZE, WelcomeScreen.HEIGHT-150);
	}
	
	/**
	 * Sets all the primary content to the ScrollPane to be displayed.
	 */
	public void createLearn() {
		
		VBox contentBox = consolidateText();
		
		contentPane = getScrollPane();
		contentPane.setContent(contentBox);
	}
	
	/**
	 * Combines all the instructions into one VBox.
	 * 
	 * @return consolidated instructions to display on the ScrollPane
	 */
	private VBox consolidateText() {
		
		VBox allText = new VBox();
		VBox introText = createText(INSTRUCTIONS_INTRO_HEADING, INSTRUCTIONS_INTRO_HEADING_SIZE, INSTRUCTIONS_INTRO_BODY);
		VBox authoringText = createText(INSTRUCTIONS_AUTHORING_HEADING, INSTRUCTIONS_CONTENT_HEADING_SIZE, INSTRUCTIONS_AUTHORING_BODY);
		VBox playerText = createText(INSTRUCTIONS_PLAYER_HEADING, INSTRUCTIONS_CONTENT_HEADING_SIZE, INSTRUCTIONS_PLAYER_BODY);
		VBox faqText = createText(INSTRUCTIONS_FAQ_HEADING, INSTRUCTIONS_CONTENT_HEADING_SIZE, INSTRUCTIONS_FAQ_BODY);
		
		allText.getChildren().addAll(introText, authoringText, playerText, faqText);
		return allText;
	}
	
	/**
	 * Acts as a factory method to create a heading and the corresponding body text for a section of the instructions.
	 * 
	 * @param heading - The heading that states the section of instructions being addressed
	 * @param headingSize - The size of the heading
	 * @param body - The body of text that details the relevant information within a section of instructions
	 * @return the VBox that contains the heading-body text pairing that constitute a complete section of instructions
	 */
	private VBox createText(String heading, String headingSize, String body) {
		
		VBox textBox = new VBox();
		Label headingText = GUITools.generateLabel(heading, WelcomeScreen.MAIN_FONT, WelcomeScreen.MAIN_COLOR, headingSize);
		Label bodyText = GUITools.generateLabel(body, WelcomeScreen.MAIN_FONT, WelcomeScreen.MAIN_COLOR, INSTRUCTIONS_BODY_SIZE);
		
		bodyText.setPrefWidth(WRAPPING_WIDTH);
		bodyText.setWrapText(true);
		headingText.setPrefWidth(WRAPPING_WIDTH);
		headingText.setWrapText(true);
		
		textBox.getChildren().addAll(headingText, bodyText);
		return textBox;
	}
	
}
