package authoring_UI.Inventory;

import java.util.List;

import authoring_UI.displayable.Displayable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class holding inventory data.
 * 
 * @author DavidTran, Dara Buggay
 *
 */
public class Inventory extends Displayable {

	public Inventory(String name, String fontType, Color fontColor, List<Pane> inventorySequence) {
		super(name, fontType, fontColor, inventorySequence);
	}

	protected String getName() {
		return super.getName();
	}
	
	public List<Pane> getInventorySequence() {
		return super.getDisplayableSequence();
	}

	protected String getFontType() {
		return super.getFontType();
	}
	
	protected Color getFontColor() {
		return super.getFontColor();
	}

}
