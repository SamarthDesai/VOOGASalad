package authoring_actionconditions;

import java.util.List;

/**
 * interface for actionconditionVBox--should not be used because that is an abstract class. A description of its methods can be found in the abstract 
 * class or in its subclasses
 * @author Owen Smith
 *
 * @param <T>--the type of object in the list that the actioncondition VBox contains
 */

public interface ActionConditionVBoxI<T> {
	
	public void removeConditionAction(int row);
	public List<T> getRows();
	public void addToRows(ActionConditionRow actionConditionRow);
	
}
