package authoring_actionconditions;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Just contains the public methods for the actionconditionHBox tab. Not actually necessary or used more than once
 * @author Owen Smith
 *
 */
public interface ActionConditionHBoxI {
	
	public Integer getRemoveValue();
	public void addButtonListener(EventHandler<ActionEvent> e);
	public void addRemoveListener(EventHandler<ActionEvent> e);
	public void addRemoveOption();
	public void removeRemoveOption(int row);
	public ObservableList<Integer> getRemoveRowVBoxOptions();
	
}
