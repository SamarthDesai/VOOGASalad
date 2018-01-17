package authoring_actionconditions;

import ActionConditionClasses.ChoiceBoxVBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class got removed from the program and was not used
 * @author Owen Smith
 *
 */

public class EditChoiceBoxVBox extends ChoiceBoxVBox {

	public EditChoiceBoxVBox(String label, ObservableList<Integer> editOptions) {
		super(label, editOptions);
	}

	protected void addRow() {
		int newSize = increaseOptionsSize();
		adjustListtoSize(newSize);
	}

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
		for (int i = 1; i <= newSize; i++) {
			newOptions.add(i);
		}
		setNewOptions(newOptions);
		setValue(null);
	}

}
