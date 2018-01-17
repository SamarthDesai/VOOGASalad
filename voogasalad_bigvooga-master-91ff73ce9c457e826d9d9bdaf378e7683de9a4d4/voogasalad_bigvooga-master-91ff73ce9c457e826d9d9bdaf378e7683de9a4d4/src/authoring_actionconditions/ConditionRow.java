package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ActionCheckBoxVBoxI;
import authoring.Sprite.AbstractSpriteObject;
import engine.Condition;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

/**
 * ConditionRow--this class is a row that contains the information for a condition for a sprite. It is a toolbar that contains a number label
 * corresponding to the row number and a treeview corresponding to the boolean condition operation that the user wants to define.
 * Assumptions--actionconditionvbox contains a list of condition rows that it manages according to how many the user creates or deletes
dependencies--depends on actionconditionrow to get methods and constructors common to it and actionrow. It also depends on the interface for 
actionCheckBoxVBox since it contains an instance of an actionCheckBoxVBox corresponding to the actions the user can associate with it
an example of how to use it--create a conditionrow with an ID, actioncheckbox vbox, and conditiontreeview, and add it to the list of conditionRows in
the conditionVBox
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ConditionRow extends ActionConditionRow implements ActionCheckBoxVBoxI {

	private static final String INVALID_SELECTED_ACTIONS_MESSAGE = "Please select valid actions";
	public static final double ROW_EXPANDED_HEIGHT = ActionConditionRow.EXPANDED_HEIGHT
			+ ConditionTreeView.INTEGER_TEXTFIELD_HEIGHT + 2 * ConditionTreeView.VBOX_SPACING;
	public static final double ROW_WIDTH = ActionConditionRow.ROW_WIDTH + 100;
	private ConditionTreeView operationTreeView;
	private ActionCheckBoxVBox actionCheckBoxVBox;
	private VBox treeViewVBox;
	private Supplier<List<AbstractSpriteObject>> supplier;

	// for creating a new row
	public ConditionRow(int ID, ObservableList<Integer> newActionOptions, ConditionVBox<ConditionRow> ACVBox,Supplier<List<AbstractSpriteObject>> supplier) {
		super(ID, ACVBox,supplier);
		addActionCheckBox(newActionOptions);
		System.out.println("supplier null in 1st conditionRow constructor " + supplier == null);
		this.setPrefSize(ROW_WIDTH, ROW_EXPANDED_HEIGHT);

		operationTreeView = new ConditionTreeView(this,supplier);
		treeViewVBox = operationTreeView.getTreeViewVBox();
		this.getItems().addAll(treeViewVBox);

	}

	// for loading from selected sprite on scene
	public ConditionRow(int ID, ObservableList<Integer> newActionOptions, List<Integer> selectedActionOptions,
			ConditionVBox<ConditionRow> ACVBox, ConditionTreeView tv,Supplier<List<AbstractSpriteObject>> supplier) {
//		this(ID, newActionOptions, ACVBox,supplier);
//		getItems().removeAll(actionCheckBoxVBox, treeViewVBox);
		super(ID, ACVBox,supplier);
		System.out.println("supplier null in 2nd conditionRow constructor " + supplier == null);
		
		this.setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
		
		actionCheckBoxVBox = new ActionCheckBoxVBox(newActionOptions, selectedActionOptions);
		treeViewVBox = tv.getTreeViewVBox();
		operationTreeView = tv;
		getItems().addAll(actionCheckBoxVBox, treeViewVBox);

	}

	// for loading from XML
	public ConditionRow(int ID, ObservableList<Integer> newActionOptions, List<Integer> selectedActionOptions,
			ConditionVBox<ConditionRow> ACVBox, String selectedOperation, Condition condition,Supplier<List<AbstractSpriteObject>> supplier) {
//		this(ID, newActionOptions, ACVBox,supplier);
//		getItems().removeAll(actionCheckBoxVBox, treeViewVBox);
		super(ID, ACVBox,supplier);
		System.out.println("supplier null in 3rd conditionRow constructor " + supplier == null);
		this.setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
		
		System.out.println("selectedActionOptions in row " + selectedActionOptions);
		actionCheckBoxVBox = new ActionCheckBoxVBox(newActionOptions, selectedActionOptions);
		operationTreeView = new ConditionTreeView(this, selectedOperation, condition,supplier);
		treeViewVBox = operationTreeView.getTreeViewVBox();
		getItems().addAll(actionCheckBoxVBox, treeViewVBox);
	}

	/********************** PUBLIC METHODS ***********************/

	/**
	 * getTreeView--returns the treeview that the condition row has to pass it into the spriteobject to store so that, if a user clicks on a sprite 
	 * object, it will load it up to be displayed and edited
	 * @return--the conditiontreeview that the conditionrow has 
	 */
	public ConditionTreeView getTreeView() {
		return operationTreeView;
	}

	/**
	 * reduceTreeView--reduces the size of the treeview in case there aren't as many elements to show or the user has finished making a treeview
	 */
	protected void reduceTreeView() {
		this.getTreeView().getRoot().setExpanded(false);
		this.getTreeView().changeRowTVSize();
	}

	/**
	 * changeRowTVSize--reduces the size of the treeview in the row
	 */
	public void changeRowTVSize() {
		operationTreeView.changeRowTVSize();
	}

	/**
	 * getCondition--returns the actual engine-defined condition that the user has made in the treeview
	 * @return
	 */
	public Condition getCondition() {
		try {
			return operationTreeView.getCondition();
		} catch (NullPointerException | NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * getSelectedActions--returns the list of actions that the user has selected
	 * throws a nullpointer exception in case the user hasn't selected any specific actions
	 */
	@Override
	public List<Integer> getSelectedActions() throws NullPointerException {
		if (((List<Integer>) actionCheckBoxVBox.getCurrentValue()).isEmpty())
			throw new NullPointerException(INVALID_SELECTED_ACTIONS_MESSAGE);
		else
			return actionCheckBoxVBox.getCurrentValue();

	}

	/**
	 * addAction()--adds an action to the conditionrow by calling the actionCheckBoxVBox to do this. This demonstrates decoupled code since it passes 
	 * off its responsibility to one of its private variable classes
	 */
	@Override
	public void addAction() {
		actionCheckBoxVBox.addAction();
	}

	/**
	 * removeAction--removes an action by calling the actionCheckBoxVBox to do so. Again, demonstrates good decoupled code
	 */
	@Override
	public void removeAction(Integer action) {
		actionCheckBoxVBox.removeAction(action);
	}

	/**
	 * setNewActionCheckBoxVBoxOptions--calls the actionCheckBoxVBox to set its options to a list of options. This would be performed if the user is 
	 * loading a spriteObject's list of action or conditions onto its tab
	 * @param newOptions
	 */
	protected void setNewActionCheckBoxVBoxOptions(ObservableList<Integer> newOptions) {
		actionCheckBoxVBox.setNewOptions(newOptions);
	}

	private void addActionCheckBox(ObservableList<Integer> newActionOptions) {
		actionCheckBoxVBox = new ActionCheckBoxVBox(newActionOptions);
		getItems().add(actionCheckBoxVBox);
	}

}
