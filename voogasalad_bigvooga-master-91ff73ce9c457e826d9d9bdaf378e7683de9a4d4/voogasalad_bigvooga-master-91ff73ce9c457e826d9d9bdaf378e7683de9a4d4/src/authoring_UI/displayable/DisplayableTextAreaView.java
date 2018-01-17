package authoring_UI.displayable;

import java.util.ArrayList;

import authoring_UI.dialogue.DialogueManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tools.DisplayLanguage;

/**
 * abstract class that creates the components that display and allow for customization of the displayable template and text areas.
 * The different buttons and options for customization also created in this class include text position, font color, background color, etc.,
 * as well as the ability to create different dialogue boxes in a sequence and toggle between them.
 * 
 * @author Dara Buggay
 *
 */
public abstract class DisplayableTextAreaView extends VBox {
	
	protected void setFont(String family, int size, ArrayList<TextArea> taList) {
		for (TextArea ta : taList) {
			ta.setFont(Font.font(family, size));
		}
	}
	
	protected void setFontColor(String color, ArrayList<TextArea> taList) {
		for (TextArea ta: taList) {
			ta.setStyle("-fx-text-fill: " + color + ";")	;
		}
	}
	
	protected void setBackgroundColor(Color color, ArrayList<Pane> paneList) {
		for (Pane pane : paneList) {
			pane.setBackground(new Background(new BackgroundFill(
                                                                 color,
                                                                 null, null)));
		}
	}
	
	protected void setBackgroundColor(Color color, Pane pane) {
		pane.setBackground(new Background(new BackgroundFill(color, null, null)));
	}	
	
	protected void setTextAreaBackgroundColor(Color color, ArrayList<TextArea> taList) {
		for (TextArea ta : taList) {
			ta.setBackground(new Background(new BackgroundFill(
                                                                 color,
                                                                 null, null)));
		}
	}
	
	protected void setBackgroundImage(Image image, ArrayList<Pane> paneList) {
		for (Pane pane : paneList) {
			BackgroundImage bgImage = new BackgroundImage(image, 
				    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				    BackgroundPosition.CENTER, 
				    new BackgroundSize(1, 1, true, true, false, false));
			pane.setBackground(new Background(bgImage));
		}
	}
	
	protected void setBackgroundImage(Image image, Pane pane) {
		BackgroundImage bgImage = new BackgroundImage(image, 
			    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.CENTER, 
			    new BackgroundSize(1, 1, true, true, false, false));
		pane.setBackground(new Background(bgImage));
	}
	
	protected void removePanel() {
		// do nothing
	}
	
	protected void addPanel() {
		// do nothing
	}
	
	protected TextArea createTextArea(double width, double height, Background bg) {
		TextArea ta = new TextArea();
		ta.setPrefSize(width, height);

		ta.setBackground(bg);
		ta.setWrapText(true);

		String css = DialogueManager.class.getResource("dialogue.css").toExternalForm();
		ta.getStylesheets().add(css);
		
		return ta;
	}
	
	protected void makeDraggableAndResizable(Object o) {
		if (o instanceof TextArea) {
			DragResizer draggable = new DragResizer((Region) o);
			draggable.makeTextAreaResizable();
			draggable.makeTextAreaDraggable();
			draggable.makeTextAreaDeletable();
		} else {
			DragImage draggable = new DragImage((ImageView) o);
			draggable.makeImageDraggable();
			draggable.makeImageDeletable();
		}
	}

	protected Pane createPane(double width, double height) {
		Pane pane = new Pane();
		pane.setPrefSize(width, height);
		
		return pane;
	}
	
	protected void setCurrentPane() {
		// do nothing
	}
	
	protected void prev() {
		// do nothing
	}
	
	protected void next() {
		// do nothing
	}
	
	protected HBox makeToolPanel() {
		return null;
	}
	
	protected HBox makeButtonPanel() {
		return null;
	}
	
	protected Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}
	
}
