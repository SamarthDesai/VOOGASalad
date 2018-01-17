package authoring_actionconditions;

import javafx.scene.control.Label;

/**
 * just the interface for action and condition rows that contians its public methods. Since it is an abstract class, should not be using an interface. 
 * Each of these public methods are explained in the class
 * @author Owen Smith
 *
 */

public interface ActionConditionRowI {
	
	public Label getLabel();
	public Label getImplementationSelectorLabel();
	public Integer getImplementationSelectorVBoxValue();
	public void decreaseLabelID();
	public int getRowID();

}
