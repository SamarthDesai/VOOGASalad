package ActionConditionClasses;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

/**
 * ChoiceBoxVBox--an extension of VBoxList. It contains a choicebox and a top label, which is similar to a VBoxList. One way is that it contains a label 
 * which can be changed depending on the choice the user selects elsewhere in the UI. It is also required to get the current choice and set the current
 * value depending on if the user is choosing something to save to the sprite object or is loading information about the spriteobject on to the sprite
 * action or condition tabs. For our game platform, it was only required that this choiceBox be of integers
 * Dependencies: vBoxList, which it extends, and choiceBovVBoxI, an interface that really should just be a public method in the class
 * Example of how to use it: create one with a label and observable list of options. The reason why these options are observable is that those changes
 * can be reflected on to the choiceBox immediately
 * @author Owen Smith
 *
 */
public class ChoiceBoxVBox extends VBoxList implements ChoiceBoxVBoxI {
	
	private ChoiceBox<Integer> choiceBox;

	public ChoiceBoxVBox(String label,ObservableList<Integer> options) {
		super(label,options);
		choiceBox = new ChoiceBox<Integer>();
		setNewOptions(options);
		getChildren().add(choiceBox);
	}

	/**
	 * getCurrentValue--like in actionCheckBoxVBox, returns the choice the user has selected. The difference here is that the user can only select one 
	 * option, whereas for the actionCheckBoxVBox the user can select multiple action
	 */
	@Override
	public Integer getCurrentValue() {
		return choiceBox.getValue();
	}

	/**
	 * setValue--sets the value of the choiceBox to be a particular choice in the list. An assumption is that this newValue is a part of the list of 
	 * options already provided by the choiceBox.
	 */
	@Override
	public void setValue(Integer newValue) {
		choiceBox.setValue(newValue);
	}

	/**
	 * realizeNewOptions--like in actionCheckBoxVBox, can set the list of options that the user can choose from to a new observable list. One assumption
	 * is that this new list of options must be an observable list since, in order to detect changes to the list, it must be observable
	 */
	@Override
	public void realizeNewOptions(ObservableList<Integer> newOptions) {
		choiceBox.setItems(newOptions);
	}
	
	/**
	 * getSelected--returns the selected option from the list
	 * @return--an integer, which the choiceBox contains, so this makes sense
	 */
	public Integer getSelected() {
		return choiceBox.getValue();
	}

}
