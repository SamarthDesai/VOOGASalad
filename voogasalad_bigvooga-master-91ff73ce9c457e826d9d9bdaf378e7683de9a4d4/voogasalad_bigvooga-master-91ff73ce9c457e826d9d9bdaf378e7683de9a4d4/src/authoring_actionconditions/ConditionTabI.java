package authoring_actionconditions;

import javafx.collections.ObservableList;

/**
 * conditionTabI--"interface" for conditionTabI, should not have been used. Methods not a part of the abstract class just should have been made public
 * @author Owen Smith
 *
 */
public interface ConditionTabI {
	
	public void addActionOption();
	public void removeActionOption(Integer action);
	public void setNewActionOptions(ObservableList<Integer> newActionOptions);
	public void addCondition(ObservableList<Integer> currentActions);

}
