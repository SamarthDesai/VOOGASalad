package ActionConditionClasses;

/**
 * ActionCheckBoxVBoxI interface--this is used by ActionCheckBoxVBox and conditionRow since conditionRow is responsible for calling its 
 * ActionCheckBoxVBox to implement these methods should a spriteobject's tab be opened 
 * Assumptions--that this list will in turn be added to or removed from, and that the conditionRow will have to get the list of selected actions for 
 * that specific condition
 * @author Owen Smith
 *
 */

public interface ActionCheckBoxVBoxI {
	
	public void addAction();
	public void removeAction(Integer action);
	public Object getSelectedActions();

}
