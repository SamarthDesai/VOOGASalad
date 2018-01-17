package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.Condition;
import engine.operations.VoogaType;
import engine.operations.booleanops.BooleanOperation;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * 
 * ConditionTreeView--creates a treeview which utilizes boolean operations to create a condition for the user. This treeview is recursive in that the 
 * user can either choose another boolean operation to define the condition or select a boolean variable/value to terminate the tree
 * @param conditionRow--the row with which the condition treeview expands with
 * @param supplier--the lambda expression supplying the information about the sprite objectst that the conditionrow will use
 * @author Owen Smith, David Tran
 *
 */
public class ConditionTreeView extends TreeView<HBox> {

	public static final double VBOX_SPACING = 10;
	public static final double INTEGER_TEXTFIELD_HEIGHT = 35;

	private static final double INTEGER_TEXTFIELD_WIDTH = 100;
	private static final double TREE_VIEW_WIDTH = ActionConditionRow.TREE_VIEW_WIDTH;
	private static final double TREE_VIEW_EXPANDED_HEIGHT = ActionConditionRow.EXPANDED_HEIGHT;
	private static final double COLLAPSED_HEIGHT = 35;
	private static final String PRIORITY_NUMBER_PROMPT = "EnterPriority";

	private OperationNameTreeItem operationNameTreeItem;
	private TextField priorityIntegerTF;
	private VBox booleanOperationTreeView;
	private ConditionRow conditionRow;

	private Condition condition;
	private String selectedOperation;
	private int priorityNumber;
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ConditionTreeView(ConditionRow conditionRow,Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		this.conditionRow = conditionRow;
		operationNameTreeItem = new OperationNameTreeItem("Boolean", "Choose Boolean Operation: ", VoogaType.BOOLEAN,
				() -> changeRowTVSize(), supplier);
		System.out.println("supplier null in conditionTreeView " + supplier == null);
		setRoot(operationNameTreeItem);
		setPrefSize(TREE_VIEW_WIDTH, TREE_VIEW_EXPANDED_HEIGHT);
		priorityIntegerTF = createIntegerTextField();
		booleanOperationTreeView = buildBooleanOperationTreeView(this);
		this.supplier = supplier;
	}

	public ConditionTreeView(ConditionRow conditionRow, String selectedOperation, Condition condition,Supplier<List<AbstractSpriteObject>> supplier) {
		this(conditionRow,supplier);
		this.selectedOperation = selectedOperation;
		System.out.println("supplier null in 2nd conditionTreeview constructor " + supplier == null);
		this.condition = condition;
		this.priorityNumber = condition.getPriority();
		this.setRoot(new TreeItem<HBox>(new HBox(new Label("Priority Number: "), new Label(Integer.toString(priorityNumber)), new Label(", Selected Operation: "), new Label(selectedOperation))));
		booleanOperationTreeView = new VBox(VBOX_SPACING);
		booleanOperationTreeView.getChildren().add(this);
		setPrefSize(TREE_VIEW_WIDTH, COLLAPSED_HEIGHT);
		 
	}

	/**
	 * setParameters--sets the tags for the conditionTreeView (ie just the first two levels, in case the user is loading it from XML)
	 * @param selectedOperation--the operation the user chose
	 * @param condition--the actual engine condition the user chose
	 */
	public void setParameters(String selectedOperation, Condition condition) {
		this.selectedOperation = selectedOperation;
		this.condition = condition;
	}

	/**
	 * getTreeViewVBox--returns the vBox within which the treeview is contained
	 * @return
	 */
	protected VBox getTreeViewVBox() {
		return booleanOperationTreeView;
	}

	/**
	 * getSelectedOperation--returns the selected condition operation of the tree
	 * @return
	 */
	public String getSelectedOperation() {
		if (selectedOperation != null)
			return selectedOperation;
		else {
			try {
				return operationNameTreeItem.getSelectedOperation();
			} catch (Exception e) {
				throw e;
			}
		}
	}

	/**
	 * getCondition--returns the condition that the user has defined from the tree. Assumes the user has selected valid options for the treeview, if 
	 * not will throw an exception which the tab will catch
	 * @return
	 */
	public Condition getCondition() {

		if (condition != null)
			return condition;

		try {
			if (priorityIntegerTF.getText().equals("")) {
				// showError(INVALID_INPUT_MESSAGE, INTEGER_INPUT_MESSAGE);
				// return null;
				throw new NumberFormatException();
			} else {
				Condition condition = new Condition(Integer.parseInt(priorityIntegerTF.getText()),
						(BooleanOperation) operationNameTreeItem.makeOperation());
				return condition;
			}

		} catch (NullPointerException e) {
			// showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
			throw e;
			// return null;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	private HBox makeIntegerInputPrompt(TextField tf) {
		Label lb = new Label();
		lb.textProperty().bind(DisplayLanguage.createStringBinding(PRIORITY_NUMBER_PROMPT));
		if (tf == null)
			;
		if (lb == null)
			;
		HBox vb = new HBox(lb, tf);
		return vb;
	}

	private VBox buildBooleanOperationTreeView(TreeView<HBox> operationTreeView) {
		VBox booleanOperationTreeView = new VBox(VBOX_SPACING);
		booleanOperationTreeView.getChildren().addAll(makeIntegerInputPrompt(priorityIntegerTF), operationTreeView);
		return booleanOperationTreeView;
	}

	/**
	 * changeRowTVSize--changes the size of the row and treeview to accomodate an expanded or collapsed view. Expanded would be if the user is editing,
	 * collapsed would be if the user has already created the tree
	 */
	protected void changeRowTVSize() {
		if (operationNameTreeItem.isExpanded()) {
			this.setPrefHeight(TREE_VIEW_EXPANDED_HEIGHT);
			conditionRow.setPrefHeight(ConditionRow.ROW_EXPANDED_HEIGHT);
		} else {
			this.setPrefHeight(COLLAPSED_HEIGHT);
			conditionRow.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}

	private void checkIntegerInput(TextField tf) {
		try {
			if (!tf.getText().equals(""))
				Integer.parseInt(tf.getText());
		} catch (NumberFormatException e) {
			// showError(INVALID_INPUT_MESSAGE, INTEGER_INPUT_MESSAGE);
			tf.clear();
			throw e;
		}
	}

	private TextField createIntegerTextField() {
		TextField tf = new TextField();
		tf.setPrefWidth(INTEGER_TEXTFIELD_WIDTH);
		tf.setOnKeyReleased(e -> {
			checkIntegerInput(tf);
		});
		return tf;
	}

	/**
	 * showError--shows an error if the user has not entered a valid input for some part of the conditionTreeView
	 * @param content
	 */
	protected static void showError(String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Invalid input");
		alert.setContentText(content);
		alert.show();
	}

}
