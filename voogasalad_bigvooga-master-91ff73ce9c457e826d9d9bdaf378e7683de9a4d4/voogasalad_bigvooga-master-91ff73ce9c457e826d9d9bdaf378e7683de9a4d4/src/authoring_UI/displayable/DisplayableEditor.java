package authoring_UI.displayable;

import java.io.File;
import engine.utilities.data.GameDataHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tools.DisplayLanguage;

/**
 * abstract class that allows users to edit/save displayables. Extended by authoring_ui dialogue, cutscene, and inventory classes
 * 
 * @author Dara Buggay
 *
 */

public abstract class DisplayableEditor {
	
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String INTEGER_INPUT_PROMPT = "InputInteger";
	
	protected Separator createSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}
	
	protected void makeTemplate() {
		// do nothing
	}
	
	protected Button createAddTextAreaButton() {
		return null;
	}
	
	protected Button createSetBackgroundButton() {
		return null;
	}
	
	protected void chooseBackgroundImage() {
		// do nothing
	}
	
	protected File retrieveFileForImageUpload(Node node) {
		Node parent = node;
		Scene s = parent.getScene();
		while (s == null) {
			parent = parent.getParent();
			s = parent.getScene();
		}
		File file = GameDataHandler.chooseFileForImageLoad(s.getWindow());
		
		return file;
	}
	
	protected void changeFontSize() {
		// do nothing
	}
	
	protected void changeFontColor() {
		// do nothing
	}
	
	protected void changeBackgroundColor() {
		// do nothing
	}
	
	protected String toRGBString(Color c) {
		return "rgb("
                + to255Int(c.getRed())
          + "," + to255Int(c.getGreen())
          + "," + to255Int(c.getBlue())
          + ")";
	}
	
    private int to255Int(double d) {
        return (int) (d * 255);
    }
    
    protected ColorPicker makeColorPallette(String color) {
		ColorPicker cp = new ColorPicker(Color.web(color));

		return cp;
	}
    
    protected ChoiceBox<String> makeChoiceBox(ObservableList<String> observableList) {
    		return null;
    }

    protected TextField makeTextField(double width, double height) {
		TextField tf = new TextField();
		tf.setPrefWidth(width);
		return tf;
	}
	
    protected HBox makeEntry(String prompt, Node tf) {
		HBox hb = new HBox(5);
		Label lb = new Label();
		lb.textProperty().bind(DisplayLanguage.createStringBinding(prompt));
		hb.getChildren().addAll(lb, tf);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}
	
    protected Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}
    
    protected Alert makeAlert() {
    		Alert alert = new Alert(AlertType.ERROR);
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(INTEGER_INPUT_PROMPT));					
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(INVALID_INPUT_MESSAGE));
		
		return alert;
    }
}

