package ActionConditionClasses;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * VBoxList--an abstract class that allows for the removal and addition of node items in a list. This is implemented in adding or removing choices from
 * the list of checkboxes of possible actions to associate each condition with and the removal of rows from the conditions and actions tabs. It is also 
 * used for adding options to a choicebox in which there a list of options a user can set. They both have a topLabel
   Assumptions--implements the VBoxListI interface. Looking back now, I just needed to use an abstract class.
Dependencies--depends on the VBoxListI interface (which it shouldn't, will be talked about more in the analysis)
An example of how to use it--create a class that extends VBoxList (the most important one being ActionCheckBoxVBox)
 * @author Owen Smith
 *
 */

public abstract class VBoxList extends VBox implements VBoxListI {

	private Label topLabel;
	private ObservableList<Integer> boxOptions;

	public VBoxList(String label, ObservableList<Integer> options) {
		super();
		setAlignment(Pos.CENTER);
		topLabel = new Label(label);
		getChildren().add(topLabel);
		boxOptions = FXCollections.observableArrayList();
		boxOptions.addListener((ListChangeListener<Integer>) c -> realizeNewOptions(boxOptions));
	}

	/**
	 * Not used, used to be used when there were choiceboxes in each row
	 */
	@Override
	public void changeLabel(String newLabel) {
		topLabel.setText(newLabel);
	}

	/**
	 * setNewOptions--given an a list of new options (used to just be generic, now it is just for the ActionCheckBoxVBox class), VBoxList will replace 
	 * the current options with the new options. 
	 * The setAll method is used because it clears the list and replaces it with the list of new options so that the list change listener will pick it 
	 * up
	 */
	@Override
	public void setNewOptions(ObservableList<Integer> newOptions) {
		if (newOptions != null)
			boxOptions.setAll(newOptions);
	}

	/**
	 * getOptionsSize()--returns the size of the options to know how to set the new size given the current size
	 */
	@Override
	public int getOptionsSize() {
		return boxOptions.size();
	}

	/**
	 * getOptions()--returns the list of options of the class that implements VBoxList
	 */
	@Override
	public ObservableList<Integer> getOptions() {
		return boxOptions;
	}

	/**
	 * addListChangeListener--gives the functionality of adding a list change listener to the VBoxList to remove or add node children to the scene given
	 * that a change occured in its internal list of options
	 */
	@Override
	public void addListChangeListener(ListChangeListener<Integer> listChangeListener) {
		boxOptions.addListener(listChangeListener);
	}
	
	/**
	 * getLabel--returns the label of the VBoxList
	 */
	@Override
	public Label getLabel() {
		return topLabel;
	}

}
