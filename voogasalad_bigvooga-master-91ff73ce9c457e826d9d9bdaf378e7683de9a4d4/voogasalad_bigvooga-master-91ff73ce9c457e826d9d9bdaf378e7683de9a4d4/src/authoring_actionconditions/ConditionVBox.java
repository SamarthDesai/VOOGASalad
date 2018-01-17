package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;
import authoring.Sprite.AbstractSpriteObject;
import javafx.collections.ObservableList;

/**
 * ConditionVBox<T>--contains the list of condition rows for a sprite object as a part of the condition tab
 * Purpose: to manage the condition rows
 * Assumptions: implements actionconditionVBox<T>, and implements conditionVBoxI<T>, which it really shouldn't since it should just have those methods 
 * as public
 * Dependencies: depends on the supplier to supply information about all sprite objects so that the treeView classes for actions and conditions have 
 * them to get important parameter information about each sprite object. These parameters could then be edited when the user makes an action or
 * condition
 * @author Owen Smith
 *
 * @param <T>--the kind of row that the VBox contains
 */
public class ConditionVBox<T> extends ActionConditionVBox<T> implements ConditionVBoxI<T>{
	
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ConditionVBox(Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		this.supplier = supplier;
	}
	
	public ConditionVBox(List<T> rows,Supplier<List<AbstractSpriteObject>> supplier) {
		super(rows);
		this.supplier = supplier;
	}

	/**
	 * addCondition--adds a condition given an integer list of actions that it will take in. Also builds a window view to create the condition or action
	 * tree and then save that
	 */
	@Override
	public void addCondition(ObservableList<Integer> currentActions) {
		ConditionRow conditionRow = new ConditionRow(getRows().size() + 1,currentActions, (ConditionVBox<ConditionRow>) this, supplier);
		addToRows(conditionRow);
		BuildConditionView bcd = new BuildConditionView(this, conditionRow);
	}
	
	/**
	 * setNewActionOptions--given a list of new action options, the vBox will call each of its rows to assign their checkboxes to the new list of action
	 * options
	 */
	@Override
	public void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		getRows().forEach(row -> ((ConditionRow) row).setNewActionCheckBoxVBoxOptions(newActionOptions));
	}
	
	/**
	 * addActionOption--for each condition row, it will add an action to that the user can select
	 */
	@Override
	public void addActionOption() {
		getRows().forEach(row -> ((ConditionRow) row).addAction());
	}
	
	/**
	 * removeActionOption--calls each condition row to remove the selected action as an option, since it was removed in the action tab
	 */
	@Override
	public void removeActionOption(Integer action) {
		getRows().forEach(row -> ((ConditionRow) row).removeAction(action));
	}
	


}
