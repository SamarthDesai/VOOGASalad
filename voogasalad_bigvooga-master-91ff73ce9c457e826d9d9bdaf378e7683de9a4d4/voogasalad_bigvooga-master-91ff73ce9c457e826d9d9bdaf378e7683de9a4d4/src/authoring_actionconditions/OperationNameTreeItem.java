package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.operations.OperationFactory;
import engine.operations.VoogaType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

/**
 * Class that represents a Operation Name TreeItem
 * 
 * @author DavidTran
 *
 */
public class OperationNameTreeItem extends TreeItem<HBox> {

	private static final String INPUT_A_DOUBLE = "Input a Double";
	private static final String INPUT_A_STRING = "Input a String";
	private static final String INPUT_A_BOOLEAN = "Input a Boolean";

	private OperationFactory operationFactory = new OperationFactory();
	private ChoiceBox<String> operationCB;
	private OperationParameterTreeItem operationParameterTreeItem;
	private List<OperationParameterTreeItem> opParameterList = new ArrayList<>();
	private Runnable changeTreeViewSize;
	private VoogaType voogaType;

	private String voogaParameterGetName;
	private String actionParameterType;
	private static List<VoogaType> voogaTypesForExistingItems;

	private Supplier<List<AbstractSpriteObject>> supplier;

	/**
	 * Constructor for creating a new item
	 * 
	 * @param actionParameterType
	 * @param voogaParameterGetName
	 * @param voogaType
	 * @param supplier
	 */
	public OperationNameTreeItem(String actionParameterType, String voogaParameterGetName, VoogaType voogaType,
			Supplier<List<AbstractSpriteObject>> supplier) {
		this.supplier = supplier;
		this.voogaType = voogaType;
		this.voogaParameterGetName = voogaParameterGetName;
		this.actionParameterType = actionParameterType;

		voogaTypesForExistingItems = new ArrayList<>();
		voogaTypesForExistingItems.add(VoogaType.ANIMATIONNAME);
		voogaTypesForExistingItems.add(VoogaType.BOOLEANNAME);
		voogaTypesForExistingItems.add(VoogaType.DIALOGNAME);
		voogaTypesForExistingItems.add(VoogaType.DOUBLENAME);
		voogaTypesForExistingItems.add(VoogaType.KEY);
		voogaTypesForExistingItems.add(VoogaType.OBJECTNAME);
		voogaTypesForExistingItems.add(VoogaType.STRINGNAME);
		voogaTypesForExistingItems.add(VoogaType.TAG);
		voogaTypesForExistingItems.add(VoogaType.WORLDNAME);

		this.makeOperationNameTreeItem();

		System.out.println("Making Operation for... ActionParameterType: " + actionParameterType
				+ " | VoogaParameterName : " + voogaParameterGetName + " | VoogaParameterType: " + voogaType);

	}

	/**
	 * Constructor that ConditionRow calls
	 * 
	 * @param actionParameterType
	 * @param actionParameterDescription
	 * @param voogaType
	 * @param changeSize
	 * @param supplier
	 */
	public OperationNameTreeItem(String actionParameterType, String actionParameterDescription, VoogaType voogaType,
			Runnable changeSize, Supplier<List<AbstractSpriteObject>> supplier) {

		this(actionParameterType, actionParameterDescription, voogaType, supplier);
		System.out.print("IS SUPPLIER NULL??????????? ");
		System.out.println(supplier == null);
		this.changeTreeViewSize = changeSize;
		this.expandedProperty().addListener(e -> changeTreeViewSize.run());

	}

	/**
	 * 
	 * @return Operation in the tree-item
	 */
	public Object makeOperation() {

		try {

			if (operationParameterTreeItem == null) {
				return operationFactory.wrap(operationCB.getSelectionModel().getSelectedItem());
			} else {
				if (operationParameterTreeItem.getNumberOfParameters() == 0) {
					System.out.println("Only textfield/noParam/ExistingItemChoiceBox for actionParameterType: "
							+ actionParameterType);
					return operationParameterTreeItem.getParameter();
				} else {
					;
					return operationParameterTreeItem.makeOperation();
				}
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 
	 * @return choice-box selection
	 */
	public String getSelectedOperation() {
		try {
			return operationCB.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			throw e;
		}
	}

	private TreeItem<HBox> makeOperationNameTreeItem() {
		HBox hb = new HBox();
		// hb.getChildren().addAll(new Label("Choose Operation: "));

		hb.getChildren().addAll(new Label(voogaParameterGetName + ": "), makeOperationNameChoiceBox(this));
		this.setValue(hb);
		this.setExpanded(true);

		return this;
	}

	private ChoiceBox<String> makeOperationNameChoiceBox(TreeItem<HBox> operationName) {
		// ObservableList<String> operations = FXCollections
		// .observableList(operationFactory.getOperations(voogaTypeString));

		if (checkVoogaType(voogaType)) {
			operationCB = new ExistingItemsChoiceBox(voogaType, supplier).getChoiceBox();
		} else {

			ObservableList<String> voogaParameters = FXCollections
					.observableList(operationFactory.getOperations(voogaType));

			List<String> newOps = new ArrayList<>();
			for (String s : voogaParameters) {
				;
				newOps.add(s.toString());

			}

			ObservableList<String> newOperations = FXCollections.observableList(newOps);

			operationCB = new ChoiceBox<>(newOperations);

			if (actionParameterType.equals("Double"))
				newOperations.add(0, INPUT_A_DOUBLE);
			else if (actionParameterType.equals("String"))
				newOperations.add(0, (INPUT_A_STRING));
			else if (actionParameterType.equals("Boolean"))
				newOperations.add(0, (INPUT_A_BOOLEAN));

			;

			operationCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

					System.out.println("Selected Operation: "
							+ newOperations.get(operationCB.getSelectionModel().getSelectedIndex()));
					operationName.getChildren().clear();
					String selectedOperation = newOperations.get(operationCB.getSelectionModel().getSelectedIndex());

					operationParameterTreeItem = new OperationParameterTreeItem(selectedOperation, supplier);

					opParameterList.add(operationParameterTreeItem);
					operationName.getChildren().add(operationParameterTreeItem);
				}
			});
		}
		return operationCB;
	}

	private boolean checkVoogaType(VoogaType type) {
		for (VoogaType voogaType : voogaTypesForExistingItems) {
			if (type == voogaType)
				return true;
		}
		return false;

	}

}
