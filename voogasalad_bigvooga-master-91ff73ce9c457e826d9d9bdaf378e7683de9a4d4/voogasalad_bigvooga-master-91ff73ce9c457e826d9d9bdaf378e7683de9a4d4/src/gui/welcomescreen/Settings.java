package gui.welcomescreen;

import java.util.Locale;

import controller.welcomeScreen.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.DisplayLanguage;

/**
 * Settings page that allows users to modify their general VOOGA application preferences.
 * 
 * @author Samarth Desai
 *
 */
public class Settings extends MenuOptionsTemplate{

	public static final int HEADING_PADDING = 10;
	private static final String SETTINGS_BUTTON_STYLE_CSS = "SettingsButtonStyle.css";
	private static final String GEARS_PATH = "Gears.gif";
	private static final int GEARS_WIDTH = 125;
	private static final int GEARS_HEIGHT = 125;
	private static final String LANGUAGE_PATH = "Language_Static.png";
	private static final int LANGUAGE_WIDTH = 100;
	private static final int LANGUAGE_HEIGHT = 100;
	private static final String LANGUAGE_HEADING = "Language";
	private static final String LANGUAGE_HEADING_SIZE = 50 + "pt;";
	private static final String ENGLISH_BUTTON = "English";
	private static final String SPANISH_BUTTON = "Spanish";
	private static final int LANGUAGE_BUTTON_HORIZONTAL_GAP = 20;
	private static final int LANGUAGE_BUTTON_VERTICAL_GAP = 20;
	private static final int SETTINGS_VERTICAL_GAP = 50;
	
	private ScrollPane contentPane = new ScrollPane();
	private ToggleGroup languageGroup = new ToggleGroup();

	/**
	 * Creates the framework of the Settings page by passing the necessary information to the MenuOptionsTemplate superclass.
	 * 
	 * @param currentStage - Stage instance that is being passed
	 * @param sceneController - Allows the correct scene to be applied, which is the settings scene
	 */
	public Settings(Stage currentStage, SceneController sceneController) {
		super(currentStage, sceneController);
		createOptionScreen(GEARS_PATH, GEARS_WIDTH, GEARS_HEIGHT, HEADING_PADDING, MenuOptionsTemplate.CONTENT_INSET_SIZE, WelcomeScreen.HEIGHT-155);
	}
	
	/**
	 * Sets all the primary content to the ScrollPane to be displayed.
	 */
	public void createSettings(){
		VBox contentBox = consolidateText();
		
		contentPane = getScrollPane();
		contentPane.setContent(contentBox);
	}
	
	/**
	 * Combines all the settings into one VBox.
	 * 
	 * @return consolidated settings to display on the ScrollPane
	 */
	private VBox consolidateText() {
		VBox allSettings = new VBox(SETTINGS_VERTICAL_GAP);
		VBox languageBox = createLanguageSetting(LANGUAGE_HEADING, LANGUAGE_HEADING_SIZE);
		allSettings.getChildren().add(languageBox);
		return allSettings;
	}
	
	/**
	 * Creates the language setting that allows users to toggle which language to display.
	 * 
	 * @param languageHeadingText - The text contained in the language heading label
	 * @param languageHeadingSize - The size of the language setting heading
	 * @return the heading and buttons for the language setting
	 */
	private VBox createLanguageSetting(String languageHeadingText, String languageHeadingSize) {
		
		Label headingText = GUITools.generateLabel(languageHeadingText, WelcomeScreen.MAIN_FONT, WelcomeScreen.MAIN_COLOR, languageHeadingSize);
		Image languageImage = GUITools.createImage(LANGUAGE_PATH, LANGUAGE_WIDTH, LANGUAGE_HEIGHT);
		ImageView language = GUITools.createImageView(languageImage);
		HBox languageSelection = createLanguageSelection();
		
		HBox settingHeading = new HBox();
		settingHeading.getChildren().addAll(headingText, language);
		settingHeading.setAlignment(Pos.CENTER);
		VBox settingBox = new VBox(LANGUAGE_BUTTON_VERTICAL_GAP);
		settingBox.getChildren().addAll(settingHeading, languageSelection);
		return settingBox;
	}
	
	/**
	 * Creates the set of language buttons that the user can select to change display language.
	 * 
	 * @return the language buttons aligned horizontally within an HBox
	 */
	private HBox createLanguageSelection() {
		
		RadioButton englishButton = createLanguageButton(ENGLISH_BUTTON, Locale.ENGLISH);
		englishButton.setSelected(true);
		RadioButton spanishButton = createLanguageButton(SPANISH_BUTTON, DisplayLanguage.SPANISH);
		
		HBox languageChoices = new HBox(LANGUAGE_BUTTON_HORIZONTAL_GAP);
		languageChoices.getChildren().addAll(englishButton, spanishButton);
		return languageChoices;
	}
	
	/**
	 * Acts as a factory method to create RadioButtons for each supported display language in VOOGA.
	 * 
	 * @param languageType - Name of language to display as the text of the label
	 * @param language - The language Locale associated with this button during creation
	 * @return the language button that allows users to select which language to display in VOOGA
	 */
	private RadioButton createLanguageButton(String languageType, Locale language) {
		RadioButton languageButton = new RadioButton(languageType);
		languageButton.getStyleClass().remove("radio-button");
		languageButton.getStyleClass().add("toggle-button");
		languageButton.getStylesheets().add(Settings.class.getResource(SETTINGS_BUTTON_STYLE_CSS).toExternalForm());
		languageButton.setToggleGroup(languageGroup);
		languageButton.textProperty().bind(DisplayLanguage.createStringBinding(languageType));
		languageButton.setOnAction(e -> DisplayLanguage.setLocale(language));
		return languageButton;
	}
}