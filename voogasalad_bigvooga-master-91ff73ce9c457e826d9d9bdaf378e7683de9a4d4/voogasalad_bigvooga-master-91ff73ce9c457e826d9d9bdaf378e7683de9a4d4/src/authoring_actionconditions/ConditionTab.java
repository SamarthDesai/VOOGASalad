package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * ConditionTab--contains and manages the conditionHox and conditionVBox classes. The actual tab that the user interacts with for a sprite object
 * @author Owen Smith
 *
 * @param <T>--the kind of row that the conditionVBox stores
 */

public class ConditionTab<T> extends ActionTab<T> implements ConditionTabI {
	
	private static final String DIALOG_TYPE = "ERROR";
	private static final String ERROR_SUMMARY = "Invalid selected actions";

	public ConditionTab(String title, Supplier<List<AbstractSpriteObject>> supplier) {
		super(title,supplier);
	}
	
//	public ConditionTab(String title,ConditionVBox<T> actionConditionVBox, ActionConditionHBox topToolBar) {
//		super(title,actionConditionVBox,topToolBar);
//	}
	
	public void displayRowExceptionMessage(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(DIALOG_TYPE);
		alert.setHeaderText(ERROR_SUMMARY);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * setActionConditionVBox--an implementation of the actionconditionVBox method to return the kind of vBox that should be stored. In this case, it 
	 * should be a conditionVBox
	 */
	@Override
	public ActionConditionVBox<T> setActionConditionVBox() {
		return new ConditionVBox<T>(getSupplier());
	}
	
	/**
	 * setNewActionOptions--did not end up being used in project
	 */
	@Override
	public void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		((ConditionVBox<T>) getActionConditionVBox()).setNewActionOptions(newActionOptions);
	}
	
	/**
	 * addActionOption--calls the conditionVBox to add an action option that the user can choose
	 */
	@Override
	public void addActionOption() {
		((ConditionVBox<T>) getActionConditionVBox()).addActionOption();
	}
	
	/**
	 * removeActionOption--calls the conditionVBox to remove an action as an option. Assumes that that action is currently in the list of actions for 
	 * that row
	 */
	@Override
	public void removeActionOption(Integer action) {
		((ConditionVBox<T>) getActionConditionVBox()).removeActionOption(action);
	}

	/**
	 * addCondition--adds a whole other empty row as a condition with the current full list of action options
	 */
	@Override
	public void addCondition(ObservableList<Integer> currentActions) {
		((ConditionVBox<T>) getActionConditionVBox()).addCondition(currentActions);
	}

}
