package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import authoring_UI.displayable.DisplayableTextAreaView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Creates the components that display and allow for customization of the inventory template and text areas.
 * The different buttons and options for customization also created in this class include text position, font color, background color, etc.,
 * as well as the ability to create different inventory boxes in a sequence and toggle between them.
 * 
 * @author David Tran, Samarth Desai, Dara Buggay
 *
 */
public class InventoryTextAreaView extends DisplayableTextAreaView {

	private static final double VBOX_SPACING = 25;
	private static final double INVENTORY_PROMPT_WIDTH = 300;
	private static final double INVENTORY_PROMPT_HEIGHT = 300;
	private static final String CLEAR_BUTTON_PROMPT = "Clear";
	
	private Button clearButton;
	private HBox inventoryPreview;

	private Runnable save;
	
	private double oldHeight = 0;
	private Supplier<Color> currentBgColor;
	private Image currentBgImage;
	private Pane pane;

	protected InventoryTextAreaView(Runnable save, Supplier <Color> bgColor) {
		currentBgColor = bgColor;
		inventoryPreview = new HBox();
		pane = new Pane();
		this.save = save;
		this.setSpacing(15);
		
		addPanel();

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(inventoryPreview, makeButtonPanel());

	}
	
	protected Pane getInventoryTemplate() {
		return pane;
	}
	
	protected void setBackgroundColor(Color color) {
		super.setBackgroundColor(color, pane);
	}
	
	protected void setBackgroundImage(Image image) {
		currentBgImage = image;
		super.setBackgroundImage(image, pane);
	}

	@Override
	protected void removePanel() {
		inventoryPreview.getChildren().clear();
		
	}

	@Override
	protected void addPanel() {
		pane = createPane(INVENTORY_PROMPT_WIDTH, INVENTORY_PROMPT_HEIGHT);
		if (currentBgImage != null) {
			setBackgroundImage(currentBgImage);
		} else {
			setBackgroundColor(currentBgColor.get());
		}
		setCurrentPane();
	}

	@Override
	protected void setCurrentPane() {
		inventoryPreview.getChildren().clear();
		inventoryPreview.getChildren().add(pane);
	}

	

	@Override
	protected HBox makeButtonPanel() {
		HBox hb = new HBox(15);
		clearButton = makeButton(CLEAR_BUTTON_PROMPT, e -> this.removePanel());
		hb.getChildren().add(clearButton);
		return hb;
	}

	@Override
	protected Button makeButton(String name, EventHandler<ActionEvent> handler) {
		return super.makeButton(name, handler);
	}

}
