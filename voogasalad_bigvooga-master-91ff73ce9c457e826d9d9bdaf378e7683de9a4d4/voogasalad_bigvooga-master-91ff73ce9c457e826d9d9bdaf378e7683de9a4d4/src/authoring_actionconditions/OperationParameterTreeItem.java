package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.operations.Operation;
import engine.operations.OperationFactory;
import engine.operations.VoogaParameter;
import engine.operations.VoogaType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

/**
 * Class representing operation parameter entry.
 * 
 * @author DavidTran
 *
 */
public class OperationParameterTreeItem extends TreeItem<HBox> {

	private static final String INPUT_A_DOUBLE = "Input a Double";
	private static final String INPUT_A_STRING = "Input a String";
	private static final String INPUT_A_BOOLEAN = "Input a Boolean";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String DOUBLE_INPUT_MESSAGE = "EnterDouble";
	private static final String BOOLEAN_INPUT_MESSAGE = "EnterBoolean";

	private OperationFactory operationFactory = new OperationFactory();
	private TextField doubleParameterTF;
	private TextField stringParameterTF;
	private TextField booleanParameterTF;
	private OperationNameTreeItem operationNameTreeItem;
	private ObservableList<String> operationParameters;
	private String selectedOperation;
	private List<Object> listOfOperations;
	private ObservableList<VoogaParameter> voogaParameters;
	public static List<VoogaType> voogaTypesForExistingItems;
	private ChoiceBox<String> existingItemsChoiceBox;
	
	private Supplier<List<AbstractSpriteObject>> supplier;

	/**
	 * Constructor
	 * 
	 * @param selectedOperation
	 * @param supplier
	 */
	public OperationParameterTreeItem(String selectedOperation, Supplier<List<AbstractSpriteObject>> supplier) {
		this.supplier = supplier;
		this.selectedOperation = selectedOperation;

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

		this.makeParameterOperationTreeItem(selectedOperation);
	}
	
	/**
	 * 
	 * @return parameter of the item.
	 */
	public Object getParameter() {

		try {
			if (doubleParameterTF != null) {
				;
				return operationFactory.wrap(getDoubleInput(doubleParameterTF));
			} else if (stringParameterTF != null) {
				;
				return operationFactory.wrap(stringParameterTF.getText());
			} else if (booleanParameterTF != null) {
				;
				return operationFactory.wrap(getBooleanInput(booleanParameterTF));
			} else if (existingItemsChoiceBox != null) {
				System.out.println("ExistingItem was inputted: "
						+ existingItemsChoiceBox.getSelectionModel().getSelectedItem().toString());
				return operationFactory.makeOperation(selectedOperation,
						operationFactory.wrap(existingItemsChoiceBox.getSelectionModel().getSelectedItem()));
			} else {
				;
				return operationFactory.makeOperation(selectedOperation, new Object[0]);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 
	 * @return recursively makes an operation if the parameter is an operation with more parameters
	 */
	public Object makeOperation() {
		// removed Operation<?> cast
		try {
			List<Object> listOfStringParams = new ArrayList<>();
			;

			for (Object op : listOfOperations) {

				if (op instanceof OperationNameTreeItem)
					listOfStringParams.add(((OperationNameTreeItem) op).makeOperation());
				else if (existingItemsChoiceBox != null) {
					listOfStringParams
							.add(operationFactory.wrap(existingItemsChoiceBox.getSelectionModel().getSelectedItem()));
				}

			}

			for (Object param : listOfStringParams) {
				;
			}

			;
			return operationFactory.makeOperation(selectedOperation, listOfStringParams.toArray());
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 
	 * @return number of parameters for this parameter (operation)
	 */
	public int getNumberOfParameters() {
		return listOfOperations.size();
	}

	private TreeItem<HBox> makeParameterOperationTreeItem(String selectedOperation) {

		HBox hb = new HBox();
		this.setValue(hb);
		makeOperationParameterChildren(selectedOperation, this, hb);
		this.setExpanded(true);
		return this;
	}

	private void makeOperationParameterChildren(String selectedOperation, TreeItem<HBox> operationParameter, HBox hb) {
		listOfOperations = new ArrayList<>();

		// ;

		if (selectedOperation.equals(INPUT_A_DOUBLE)) {
			doubleParameterTF = createDoubleTextField(operationParameter);
			hb.getChildren().addAll(doubleParameterTF);

		} else if (selectedOperation.equals(INPUT_A_STRING)) {
			stringParameterTF = createStringTextField(operationParameter);
			hb.getChildren().addAll(stringParameterTF);
		}

		else if (selectedOperation.equals(INPUT_A_BOOLEAN)) {
			booleanParameterTF = createBooleanTextField(operationParameter);
			hb.getChildren().addAll(booleanParameterTF);
		}

		else {
			operationParameters = FXCollections.observableList(operationFactory.getParameters(selectedOperation));
			voogaParameters = FXCollections.observableList(operationFactory.getParametersWithNames(selectedOperation));
			;
			// listOfOperations = new ArrayList<>();

			if (!operationParameters.isEmpty()) {

				hb.getChildren().add(new Label("Choose Operation Parameter(s): "));
				hb.getChildren().add(new Label("[ "));

				for (int i = 0; i < operationParameters.size(); i++) {
					hb.getChildren().add(new Label(operationParameters.get(i) + " "));
					
					;

					if (this.checkVoogaType(voogaParameters.get(i).getType())) {

						;
						existingItemsChoiceBox = new ExistingItemsChoiceBox(voogaParameters.get(i).getType(), supplier)
								.getChoiceBox();

						listOfOperations.add(existingItemsChoiceBox);

						operationParameter.getChildren().add(new TreeItem<HBox>(
								new HBox(new Label(voogaParameters.get(i).getName() + ": "), existingItemsChoiceBox)));

					} else {

						operationNameTreeItem = new OperationNameTreeItem(operationParameters.get(i),
								voogaParameters.get(i).getName(), voogaParameters.get(i).getType(), supplier);
						listOfOperations.add(operationNameTreeItem);
						operationParameter.getChildren().add(operationNameTreeItem);
					}
				}
				hb.getChildren().add(new Label("]"));
			}

		}
	}

	private boolean checkVoogaType(VoogaType type) {
		for (VoogaType voogaType : voogaTypesForExistingItems) {
			if (type == voogaType)
				return true;
		}
		return false;

	}

	private TextField createDoubleTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert
		// Double: "), tf));
		// tf.setOnKeyReleased(e -> {
		// checkDoubleInput(tf);
		// checkEmptyInput(tf, parameterAction, paramTV,
		// parameterAction.getChildren().indexOf(tfTreeItem));
		// });

		return tf;
	}

	private TextField createStringTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert
		// String: "), tf));
		tf.setOnKeyReleased(e -> { // checkEmptyInput(tf, parameterAction, paramTV,
			// parameterAction.getChildren().indexOf(tfTreeItem));
		});

		return tf;
	}

	private TextField createBooleanTextField(TreeItem<HBox> operationParameter) {
		TextField tf = new TextField();
		return tf;
	}

	private Double getDoubleInput(TextField tf) {
		try {
			if (!tf.getText().equals(""))
				return Double.parseDouble(tf.getText());
			else
				return null;
		} catch (NumberFormatException e) {
			showError(INVALID_INPUT_MESSAGE, DOUBLE_INPUT_MESSAGE);
			tf.clear();
			return null;
		}
	}

	private Boolean getBooleanInput(TextField tf) {

		if (tf.getText().toLowerCase().equals("true") || tf.getText().toLowerCase().equals("false"))
			return Boolean.parseBoolean(tf.getText().toLowerCase());
		else {
			showError(INVALID_INPUT_MESSAGE, BOOLEAN_INPUT_MESSAGE);
			// tf.clear();
			return null;
		}
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

}
