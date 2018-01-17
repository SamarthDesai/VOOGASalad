package authoring_UI.displayable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * abstract class that represents the pane containing all displayable authoring components. Extended by authoring_ui dialogue, cutscene, and inventory classes
 * 
 * @author Dara Buggay
 *
 */

public abstract class DisplayableManager {
	
	public DisplayableManager() {
		
	}
	
	protected Separator createSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		separator.setPrefHeight(660);
		separator.setMaxHeight(660);
		separator.setMinHeight(660);
		return separator;
	}
	
	protected Separator createShortSeparator(int height) {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		separator.setPrefHeight(height);
		separator.setMaxHeight(height);
		separator.setMinHeight(height);
		return separator;
	}
	
	protected void updateListView() {
		// do nothing
	}
	
	protected void save() {
		// do nothing
	}
	
	protected void newEditor() {
		// do nothing
	}
	
	protected void loadEditor() {
		// do nothing
	}
	
	protected VBox createButtonPanel() {
		return null;
	}
	
	protected void prev() {
		// do nothing
	}
	
	protected void next() {
		// do nothing
	}
	
	protected Button createButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}
	
	protected void delete() {
		// do nothing
	}
	

}
