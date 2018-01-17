package authoring.Sprite.Parameters;

import javafx.scene.layout.HBox;

/**
 * abstract class for UI component of a sprite parameter- hbox of name + value
 * 
 * @author Dara Buggay
 *
 */
public abstract class FEParameter extends HBox {

	protected abstract void handleValueChange();
	
	public abstract void updateParameter();
	
}
