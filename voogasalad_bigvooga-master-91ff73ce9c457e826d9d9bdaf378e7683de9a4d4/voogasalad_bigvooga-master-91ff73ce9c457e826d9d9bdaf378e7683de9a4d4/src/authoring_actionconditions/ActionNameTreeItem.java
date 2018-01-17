package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.Action;
import engine.Actions.ActionFactory;
import engine.operations.Operation;
import engine.operations.VoogaParameter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

/**
 * Class representing the second tree-item for action tree view.
 * 
 * @author DavidTran
 *
 */
public class ActionNameTreeItem extends TreeItem<HBox> {

	private static String EMPTY_INPUT = "EmptyInput";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String INPUT_A_DOUBLE = "InputInteger";

	private ActionFactory actionFactory = new ActionFactory();
	private List<OperationNameTreeItem> opNameTreeItemList;
	private List<Object> operationList;
	private String selectedAction;
	private Action action;

	private Supplier<List<AbstractSpriteObject>> supplier;
	// private OperationNameTreeItem operationNameTreeItem1;
	// private OperationNameTreeItem operationNameTreeItem2;

	/**
	 * Constructor
	 * 
	 * @param actionCategory
	 * @param supplier
	 */
	public ActionNameTreeItem(String actionCategory, Supplier<List<AbstractSpriteObject>> supplier) {
		this.supplier = supplier;
		this.makeActionTreeItem(actionCategory);
	}

	/**
	 * 
	 * @return action from the operations in the lower tree view tiers
	 */
	public Action extract() {
		operationList = new ArrayList<>();
		try {
			for (OperationNameTreeItem opItem : opNameTreeItemList) {

				operationList.add(opItem.makeOperation());
				;

			}
			// ;
			;
			action = actionFactory.makeAction(selectedAction, operationList.toArray());
			return action;
		} catch (NullPointerException e) {
			throw e;
			// showError(INVALID_INPUT_MESSAGE, EMPTY_INPUT);
		} catch (NumberFormatException e) {
			throw e;
			// showError(INVALID_INPUT_MESSAGE, INPUT_A_DOUBLE);
		}
		// return null;
	}
	
	/**
	 * 
	 * @return action name choice-box selections
	 */
	public String getSelectedAction() {
		return selectedAction;
	}

	private TreeItem<HBox> makeActionTreeItem(String actionCategory) {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action: "), makeActionNameChoiceBox(actionCategory, this));
		this.setValue(hb);
		this.setExpanded(true);
		return this;
	}

	private ChoiceBox<String> makeActionNameChoiceBox(String actionCategory, TreeItem<HBox> actionTreeItem) {
		ObservableList<String> actions = FXCollections.observableList(actionFactory.getActions(actionCategory));
		ChoiceBox<String> cb = new ChoiceBox<>(actions);
		;

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// ;
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				actionTreeItem.getChildren().clear();
				selectedAction = actions.get(cb.getSelectionModel().getSelectedIndex());
				actionTreeItem.getChildren().add(makeActionParameterTreeItem(selectedAction));
			}
		});
		return cb;
	}

	private TreeItem<HBox> makeActionParameterTreeItem(String action) {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("Choose Action Parameter(s)/Operation(s): "));

		TreeItem<HBox> parameterAction = new TreeItem<HBox>(hb);
		makeActionParameterChildren(action, parameterAction, hb);
		parameterAction.setExpanded(true);
		return parameterAction;
	}

	private void makeActionParameterChildren(String action, TreeItem<HBox> parameterAction, HBox hb) {
		
		System.out.println("ACTION: " + action);
		
		ObservableList<String> actionParameterTypes = FXCollections.observableList(actionFactory.getParameters(action));

		ObservableList<VoogaParameter> voogaParameters = FXCollections
				.observableList(actionFactory.getParametersWithNames(action));
		;
		
		for (VoogaParameter vp : voogaParameters) {
			System.out.println("ACTION PARAMETER TYPE: " + vp.getType().getEngineType());
		}
		
		opNameTreeItemList = new ArrayList<>();

		hb.getChildren().add(new Label("[ "));

		for (int i = 0; i < actionParameterTypes.size(); i++) {
			hb.getChildren().add(new Label(actionParameterTypes.get(i) + " "));
			
//			
//					+ " | VoogaParameterName : " + voogaParameters.get(i).getName() + " | VoogaParameterType: "
//					+ voogaParameters.get(i).getType());

			OperationNameTreeItem opNameTreeItem = new OperationNameTreeItem(actionParameterTypes.get(i),
					voogaParameters.get(i).getName(), voogaParameters.get(i).getType(), supplier);

			opNameTreeItemList.add(opNameTreeItem);
			parameterAction.getChildren().add(opNameTreeItem);
		}
		// for (String param : actionParameterTypes) {
		// hb.getChildren().add(new Label(param + " "));
		//
		// OperationNameTreeItem opNameTreeItem = new OperationNameTreeItem(param);
		// opNameTreeItemList.add(opNameTreeItem);
		// parameterAction.getChildren().add(opNameTreeItem);
		//
		// }

		hb.getChildren().add(new Label("]"));
	}
}
