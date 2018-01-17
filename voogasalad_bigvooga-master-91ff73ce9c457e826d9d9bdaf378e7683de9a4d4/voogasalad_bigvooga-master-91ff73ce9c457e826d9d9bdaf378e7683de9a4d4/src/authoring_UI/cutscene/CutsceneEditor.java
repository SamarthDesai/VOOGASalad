package authoring_UI.cutscene;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import authoring_UI.displayable.DisplayableEditor;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import tools.DisplayLanguage;
import authoring_UI.dialogue.*;

/**
 * Class that allows users to edit/save cutscenes
 * 
 * @author Dara Buggay
 */
public class CutsceneEditor extends DisplayableEditor {

	private static final String NAME_PROMPT = "Name";
	private static final String FONT_TYPE_PROMPT = "FontType";
	private static final String FONT_SIZE_PROMPT = "FontSize";
	private static final String FONT_COLOR_PROMPT = "FontColor";
	private static final String BACKGROUND_COLOR_PROMPT = "BackgroundColor";
	private static final String NUM_PANELS_PROMPT = "NumberOfPanels";
	private static final String INITIAL_FONT_COLOR = "#47BDFF";
	private static final String INITIAL_BACKGROUND_COLOR = "#FFFFFF";

	private static final double NAME_PROMPT_WIDTH = 150;
	private static final double FONT_PROMPT_WIDTH = 50;
	private static final double FONT_SIZE_PROMPT_WIDTH = 50;
	private static final double NUM_PANELS_PROMPT_WIDTH = 50;
	private static final double PROMPT_HEIGHT = 10;
	private static final double INPUT_HBOX_HEIGHT = 100;

	private VBox view;
	private TextField nameTF;
	private TextField sizeTF;
	private ChoiceBox<String> fontTypeCB;
	private ColorPicker fontColorCP;
	private TextField numPanelsTF;
	private CutsceneTextAreaView cutsceneView;
	private Consumer<String> saveConsumer;
	private ColorPicker backgroundColorCP;
	private SVGPath svg;
	private Image image;
	private GameDataHandler GDH;
	private boolean backgroundIsColor;
	private String currentFile;

	public CutsceneEditor(Consumer<String> saveCons, GameDataHandler GDH) {
		this.saveConsumer = saveCons;
		this.GDH = GDH;

		view = new VBox(10);
		view.getStylesheets().add(DialogueManager.class.getResource("dialogue.css").toExternalForm());

		this.makeTemplate();
	}

	protected VBox getParent() {
		return view;
	}

	protected String getName() {
		return nameTF.getText();
	}

	protected String getFontType() {
		return fontTypeCB.getSelectionModel().getSelectedItem();
	}

	protected int getFontSize() {
		if (sizeTF.getText().equals(""))
			return 16;
		else
			return Integer.parseInt(sizeTF.getText());
	}

	protected Color getFontColor() {
		return fontColorCP.getValue();
	}
	
	protected List<Pane> getCutsceneSequence() {
		return cutsceneView.getCutsceneSequence();
	}
	
	protected String getBackgroundColor() {
		return backgroundColorCP.getValue().toString();
	}
	
	protected VBox getView() {
		System.out.println(view.getHeight());
		return view;
	}
	
	protected String getBackgroundImage() {
		return currentFile;
	}

	/*************************** PRIVATE METHODS *********************************/

	private void save(String name) {
		if (!name.trim().equals(""))
			saveConsumer.accept(name);
	}

	@Override
	protected void makeTemplate() {
		this.makeInputFields();

		HBox textHBox = new HBox(5);
		textHBox.setAlignment(Pos.CENTER);
		textHBox.getChildren().addAll(createImageButton(), createSeparator(), 
					createAddTextAreaButton(), createSeparator(),
				  new HBox(makeEntry(FONT_COLOR_PROMPT, fontColorCP)),
				  createSeparator(),
				  new HBox(makeEntry(FONT_TYPE_PROMPT, fontTypeCB)),
				  createSeparator(),
				  new HBox(makeEntry(FONT_SIZE_PROMPT, sizeTF)));
		
		HBox backgroundHBox = new HBox(5);
		backgroundHBox.getChildren().addAll(new HBox(makeEntry(BACKGROUND_COLOR_PROMPT, backgroundColorCP)), 
											createSeparator(), createSetBackgroundButton());
		
		VBox cutsceneModifiersBox = new VBox(20);
		cutsceneModifiersBox.getChildren().addAll(new HBox(makeEntry(NAME_PROMPT, nameTF)), textHBox, backgroundHBox);
		
		view.getChildren().addAll(cutsceneModifiersBox, cutsceneView);
	}
	
	protected Button createImageButton() {
		Button addImage = new Button("Add Image");
		addImage.setOnAction(e -> cutsceneView.addImage());
		
		return addImage;
	}

	@Override
	protected Button createAddTextAreaButton() {
		Button addText = new Button("Add Text");
		addText.setOnAction(e -> cutsceneView.addTextArea());
		
		return addText;
	}
	
	@Override
	protected Button createSetBackgroundButton() {
		Button setBackground = new Button("Set Background");
		setBackground.setOnAction(e -> chooseBackgroundImage());
		
		return setBackground;
	}
	
	@Override
	protected void chooseBackgroundImage() {
		File file = retrieveFileForImageUpload(this.getParent());
		if (file != null) {
			currentFile = file.getName();
			image =  GDH.getImage(file);
			cutsceneView.setBackgroundImage(image);
			backgroundColorCP.setValue(null);
			backgroundIsColor = false;
		}
	}

	private void makeInputFields() {
		nameTF = makeTextField(NAME_PROMPT_WIDTH, PROMPT_HEIGHT);
		sizeTF = makeTextField(FONT_SIZE_PROMPT_WIDTH, PROMPT_HEIGHT);
		fontTypeCB = makeChoiceBox(FXCollections.observableList(Font.getFamilies()));
		fontColorCP = makeColorPallette(INITIAL_FONT_COLOR);
		backgroundColorCP = makeColorPallette(INITIAL_BACKGROUND_COLOR);

		sizeTF.setOnKeyReleased(e -> changeFontSize());
		fontColorCP.setOnAction(e -> changeFontColor());
		backgroundColorCP.setOnAction(e -> changeBackgroundColor());

		numPanelsTF = makeTextField(NUM_PANELS_PROMPT_WIDTH, PROMPT_HEIGHT);

		cutsceneView = new CutsceneTextAreaView(() -> saveConsumer.accept(getName()), () -> backgroundColorCP.getValue(), GDH, this);
	}
	
	@Override
	protected void changeFontSize() {
		if (!sizeTF.getText().equals("")) {

			try {
				int size = Integer.parseInt(sizeTF.getText());
				saveConsumer.accept(getName());
				System.out.println("size changed! saving!");
				cutsceneView.setFont(getFontType(), size);
			} catch (NumberFormatException ex) {
				sizeTF.clear();
				makeAlert().show();
				}
			}
		cutsceneView.setFont(getFontType(), getFontSize());
	}
	
	@Override
	protected void changeFontColor() {
		cutsceneView.setFontColor(toRGBString(fontColorCP.getValue()));
    }
    
	@Override
	protected void changeBackgroundColor() {
		currentFile = null;
		cutsceneView.setBackgroundColor(backgroundColorCP.getValue());
		backgroundIsColor = true;
    }

	@Override
	protected ChoiceBox<String> makeChoiceBox(ObservableList<String> observableList) {
		ChoiceBox<String> cb = new ChoiceBox<String>(observableList);
		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				saveConsumer.accept(getName());
				System.out.println("font changed! saving!");
				cutsceneView.setFont(observableList.get(cb.getSelectionModel().getSelectedIndex()), getFontSize());
			}
		});
		return cb;

	}
	
	protected File getFileForImageUpload() {
		File file = super.retrieveFileForImageUpload(this.getParent());
		
		return file;
	}
	
	protected boolean getBackgroundIsColor () {
		return backgroundIsColor;
	}

}

