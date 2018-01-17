package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.Action;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

/**
 * ActionTreeView--very similar to conditionTreeView except that it uses action operations from the engine action factory instead of boolean operations
 * Purpose: to create a treeView that the user can use to create actions using the action operation factory
 * Assumptions: is a part of an actionRow, and will launch a window with the action row 
 * Dependencies: on actionRow, actionCategory Tree Item (the first item in the treeview)
 * Example of how to use: instantiate either with the operation title and supplier of abstract sprite object information the map if the user is loading
 * information about it or without the titles if the user is creating a new one
 * @author Owen Smith, David Tran
 *
 */
public class ActionTreeView extends TreeView<HBox> {

	private static final double TREE_VIEW_WIDTH = ActionConditionRow.TREE_VIEW_WIDTH;
	private static final double COLLAPSED_HEIGHT = ActionConditionRow.COLLAPSED_HEIGHT;
	private static final double EXPANDED_HEIGHT = ActionConditionRow.EXPANDED_HEIGHT;
	private ActionCategoryTreeItem categoryAction;
	private ActionRow actionRow;
	private String categoryName;
	private String actionName;
	private Action action;
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ActionTreeView(ActionRow actionRow,Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		this.supplier = supplier;
		this.actionRow = actionRow;
		categoryAction = new ActionCategoryTreeItem(() -> changeRowTVSize(), supplier);
		setRoot(categoryAction);
		setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
		
	}

	public ActionTreeView(ActionRow actionRow, List<String> params, Action action,Supplier<List<AbstractSpriteObject>> supplier) {
		this(actionRow,supplier);
		
		this.supplier = supplier;
		this.action = action;
		this.categoryName = params.get(0);
		this.actionName = params.get(1);
		this.setRoot(new TreeItem<HBox>(new HBox(new Label("Category: "), new Label(categoryName),
				new Label(", Action: "), new Label(actionName))));
		setPrefSize(TREE_VIEW_WIDTH, COLLAPSED_HEIGHT);
	}

	/**--setParameters--sets these parameters if loaded from a saved game
	 * params--the first few entries in the tree if loading from data, since not the whole treeview can be re-created
	 * action--the user saved action
	 * @param params
	 * @param action
	 */
	public void setParameters(List<String> params, Action action) {
		this.action = action;
		this.categoryName = params.get(0);
		this.actionName = params.get(1);
	}

	/**
	 * returns the first entry--the category name--to save part of the treeview. Throws nullpointer if user has not chosen it
	 * @return
	 * @throws NullPointerException
	 */
	public String getCategoryName() throws NullPointerException {
		if (categoryName != null)
			return categoryName;
		else {
			if (categoryAction.getSelectedCategory() != null)
				return categoryAction.getSelectedCategory();
			else
				throw new NullPointerException();
		}
	}

	/**
	 * getActionName--returns the second entry
	 * @return--an String corresponding to this name
	 * @throws NullPointerException--if action is (user left the field blank)
	 */
	public String getActionName() throws NullPointerException {
		if (actionName != null)
			return actionName;
		else {
			if (categoryAction.getSelectedAction() != null)
				return categoryAction.getSelectedAction();
			throw new NullPointerException();
		}
	}

	/**
	 * gets the engine action that the user has defined for this treeview. Throws exception if not all fields are filled in correctly or some are left
	 * blank
	 * @return--an engine action that is serializable/exportable
	 */
	public Action getAction() {

		if (action != null)
			return action;
		else {
			try {
				Action action = categoryAction.extract();
				if (action == null)
					System.out.println("NULL ACTION");
				return action;
			} catch (NullPointerException e) {
				// showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
				throw e;
			} catch (NumberFormatException e) {
				throw e;
			}
		}
	}

	/**
	 * changeRowTVSize--changes the size of the treeview depending on if the user is done creating it. Expanded if in window, closed if in tab and done
	 * creating but might still want to edit
	 */
	public void changeRowTVSize() {
		if (categoryAction.isExpanded()) {
			setPrefHeight(EXPANDED_HEIGHT);
			actionRow.setPrefHeight(EXPANDED_HEIGHT);
		} else {
			setPrefHeight(COLLAPSED_HEIGHT);
			actionRow.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

}
