package authoring_actionconditions;

import ActionConditionClasses.ChoiceBoxVBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * RemoveChoiceBoxVBox--the choiceBox in the ActionConditionHBox of each action or condition tab that has the list of current action and conditions 
 * (numbered one through however many actions or conditions there are). This way, the user can delete sprite actions or conditions simply by selecting 
 * the action or condition row he/she wants to remove and then clicking the remove button, which is a part of the ActionConditionHBox class.
 * Assumptions: depends on the superclass choiceBoxVBox, which extends VBoxList
 * Dependencies: on ChoiceBoxVBox
 * Example of how to use it: create an instance of it with a label and observable integer list of options, just like for actionCheckBoxVBox
 * @author Owen Smith
 *
 */
public class RemoveChoiceBoxVBox extends ChoiceBoxVBox {
	
	public RemoveChoiceBoxVBox(String label,ObservableList<Integer> removalOptions) {
		super(label,removalOptions);
	}
	
	/**
	 * addRow()--adds an option to the choiceBox
	 */
	protected void addRow() {
		int newSize = increaseOptionsSize();
		adjustListtoSize(newSize);
	}
	
	/**
	 * removeRow()--removes an option from the choiceBox
	 */
	protected void removeRow() {
		int newSize = decreaseOptionsSize();
		adjustListtoSize(newSize);
	}
	
	private int increaseOptionsSize() {
		return getOptionsSize() + 1;
	}
	
	private int decreaseOptionsSize() {
		return getOptionsSize() - 1;
	}
	
	private void adjustListtoSize(int newSize) {
		ObservableList<Integer> newOptions = FXCollections.observableArrayList();
		for(int i = 1; i <= newSize; i++) {
			newOptions.add(i);
		}
		setNewOptions(newOptions);
		setValue(null);
	}
	
}
