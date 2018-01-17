package authoring_UI.Inventory;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import authoring_UI.dialogue.DialogueManager;
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

/**
 * Class that allows users to edit/save inventories
 * 
 * @author DavidTran, Dara Buggay
 */
public class InventoryEditor extends DisplayableEditor {

	private static final String BACKGROUND_COLOR_PROMPT = "BackgroundColor";
	private static final String INITIAL_BACKGROUND_COLOR = "#FFFFFF";

	private VBox view;
	private InventoryTextAreaView isp;
	private Consumer<String> saveConsumer;
	private ColorPicker backgroundColorCP;
	private SVGPath svg;
	private Image image;
	private String currentFile;
	private Color bgColor;
	private GameDataHandler GDH;
	private boolean backgroundIsColor;

	public InventoryEditor(Consumer<String> saveCons, GameDataHandler currentGDH) {
		this.saveConsumer = saveCons;
		GDH = currentGDH;
		view = new VBox(10);
		view.getStylesheets().add(DialogueManager.class.getResource("dialogue.css").toExternalForm());

		this.makeTemplate();
	}

	protected VBox getParent() {
		return view;
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
		
		HBox backgroundHBox = new HBox(5);
		backgroundHBox.getChildren().addAll(new HBox(makeEntry(BACKGROUND_COLOR_PROMPT, backgroundColorCP)), 
											createSeparator(), createSetBackgroundButton());
		
		VBox inventoryModifiersBox = new VBox(20);
		inventoryModifiersBox.getChildren().add(backgroundHBox);
		
		view.getChildren().addAll(inventoryModifiersBox, isp);
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
		System.out.println("FILES SUCK" + file);
		if (file != null) {
			currentFile = file.getName();
			image =  GDH.getImage(file);
			isp.setBackgroundImage(image);
			backgroundColorCP.setValue(null);
			backgroundIsColor = false;
		}
	}
	

	private void makeInputFields() {
		backgroundColorCP = makeColorPallette(INITIAL_BACKGROUND_COLOR);
		backgroundColorCP.setOnAction(e -> changeBackgroundColor());

		isp = new InventoryTextAreaView(() -> saveConsumer.accept("Inventory Template"), () -> backgroundColorCP.getValue());
	}
	
	@Override
	protected void changeBackgroundColor() {
		currentFile = null;
		isp.setBackgroundColor(backgroundColorCP.getValue());
		backgroundIsColor = true;
    }

	protected boolean getBackgroundIsColor () {
		return backgroundIsColor;
	}

}
