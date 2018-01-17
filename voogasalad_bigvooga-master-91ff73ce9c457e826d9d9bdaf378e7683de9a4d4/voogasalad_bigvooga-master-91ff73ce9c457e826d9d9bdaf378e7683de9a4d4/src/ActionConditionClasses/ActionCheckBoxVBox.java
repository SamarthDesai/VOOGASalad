package ActionConditionClasses;

import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

/**
 * ActionCheckBoxVBox--a VBox that contains a vertical list of all checkboxes numbered 1 through however many actions the user has defined. 
 * Each condition can be associated with multiple actions, so checking which actions correspond with which conditions would allow the user to 
 * accomplish this task. 
 * This class is assumed to extend the generic VBoxList class, which contains a VBox with a label and an abstract list of objects which can change (ie 
 * the user can delete or add to it one at a time).
 * It depends on the abstract VBoxList class and ActionCheckBoxVBoxI interface.
 * Example of how to use: either define the class with an observable list of options or a list of selected options.
 * @author Owen Smith
 *
 */

public class ActionCheckBoxVBox extends VBoxList implements ActionCheckBoxVBoxI {

	private ObservableList<CheckBox> checkBoxes;
	private static final String ASSOCIATED_ACTIONS = "Associated actions";

	public ActionCheckBoxVBox(ObservableList<Integer> options) {
		super(ASSOCIATED_ACTIONS, options);
		checkBoxes = FXCollections.observableList(new LinkedList<CheckBox>());
		checkBoxes.addListener((ListChangeListener<CheckBox>) c -> addOrRemoveCheckBoxes(c));
		setNewOptions(options);
	}
	
	public ActionCheckBoxVBox(ObservableList<Integer> options,List<Integer> selectedOptions) {
		this(options);
		ObservableList<CheckBox> tempCheckBoxes = FXCollections.observableArrayList(checkBoxes);
		System.out.println("selectedOptions size " + selectedOptions.size());
		for(Integer selectedOption : selectedOptions) {
			System.out.println("selected option " + selectedOption);
			int selInt = (int) selectedOption;
			tempCheckBoxes.get(selInt - 1).setSelected(true);
		}
		checkBoxes.setAll(tempCheckBoxes);
	}

	/**
	 * getCurrentValue--to get the current list of selected options from the checkbox. This will return the list of actions that the user has selected
	 * to correspond with that condition.
	 * Assumes that the user has selected at least one action (error checking is done beforehand to assure of this)
	 */
	@Override
	public List<Integer> getCurrentValue() {
		List<Integer> checkedBoxValues = new LinkedList<Integer>();
		for (CheckBox checkBox : checkBoxes) {
			if (checkBox.isSelected())
				checkedBoxValues.add(Integer.parseInt(checkBox.getText()));
		}
		return checkedBoxValues;
	}
	
	
	/**
	 * realizeNewOptions--an inherited method from VBoxList that sets the list in its subclass to a new list of options
	 * Assumptions: newOptions is a list of integers from 1 to the new list of actions
	 */
	@Override
	public void realizeNewOptions(ObservableList<Integer> newOptions) {
		ObservableList<CheckBox> newCheckBoxes = FXCollections.observableArrayList();
		newOptions.forEach(newOption -> newCheckBoxes.add(new CheckBox(newOption.toString())));
		checkBoxes.setAll(newCheckBoxes);
	}

	private void addOrRemoveCheckBoxes(Change<? extends CheckBox> c) {
		while(c.next()) {
			for(CheckBox checkBox : c.getRemoved()) getChildren().remove(checkBox);
			for(CheckBox checkBox : c.getAddedSubList()) getChildren().add(checkBox);
		}
	}

	/**
	 * addAction--adds a new checkbox whose number is one more than the current size of the list of current checkboxes
	 */
	@Override
	public void addAction() {
		checkBoxes.add(new CheckBox(Integer.toString(checkBoxes.size() + 1)));
	}

	
	public void removeAction(Integer action) {
		checkBoxes.remove((int) action);
		for(int i = (int) action; i < checkBoxes.size(); i++) {
			CheckBox iCheckBox = checkBoxes.get(i);
			int currentLabel = Integer.parseInt(iCheckBox.getText());
			iCheckBox.setText(Integer.toString(currentLabel - 1));
		}
	}
	
	/**
	 * getSelectedActions--just getCurrentValue, was used instead of getCurrentValue because of some conflict with another class that uses the same 
	 * interface
	 */
	@Override
	public Object getSelectedActions() {
		return getCurrentValue();
	}

}
