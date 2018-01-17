package authoring_actionconditions;

import javafx.collections.ObservableList;

/**
 * conditionVBoxI<T>--really should have implemented a similar interface to the actioncheckBox vBox since so many of those methods are similar. 
 * Any methods not covered with those should just be public methods specific to the conditionVBox class
 * @author Owen Smith
 *
 * @param <T>
 */
public interface ConditionVBoxI<T> {
	
	public void addCondition(ObservableList<Integer> currentActions);
	public void addActionOption();
	public void removeActionOption(Integer action);
	public void setNewActionOptions(ObservableList<Integer> newActionOptions);

}
