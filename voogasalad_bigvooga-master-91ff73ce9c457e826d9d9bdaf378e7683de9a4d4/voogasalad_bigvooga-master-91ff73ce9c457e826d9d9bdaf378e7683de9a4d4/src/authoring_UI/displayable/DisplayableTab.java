package authoring_UI.displayable;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * abstract class that represents a tab listing displayables to edit. Extended by authoring_ui dialogue, cutscene, and inventory classes
 * 
 * @author Dara Buggay
 *
 */
public abstract class DisplayableTab extends Tab {
	
	protected DisplayableTab(String name) {
		this.textProperty().bind(DisplayLanguage.createStringBinding(name));
	}
	
	protected VBox makeVBox(double width, double height, double spacing) {
		VBox hb = new VBox(spacing);
		hb.setPrefWidth(width);
		hb.setPrefHeight(height);
		return hb;
	}
	
	protected void addDisplayable(int index, Button btn) {
		// do nothing
	}
	
	protected void deleteDisplayable(int index) {
		// do nothing
	}
	
	protected int getButtonIndex(Button btn) {
		return 0;
	}
	

}
