package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.Action;

/**
 * ActionRow--a row that contains all of the actions for a sprite object all in the action tab. This extends actionConditionRow. The only difference 
 * between these rows and the condition rows is that the condition rows have boolean operation tree views and checkboxes to associate each condition 
 * with actions, whereas the action rows have action operation treeviews without the checkboxes
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ActionRow extends ActionConditionRow {

	private ActionTreeView actionTreeView;
	public static final double ROW_EXPANDED_HEIGHT = ActionConditionRow.EXPANDED_HEIGHT + 50;

	// for creating a new row
	public ActionRow(int ID, ActionVBox<ActionRow> ACVBox,Supplier<List<AbstractSpriteObject>> supplier) {
		super(ID, ACVBox,supplier);
		setPrefSize(ROW_WIDTH, ROW_EXPANDED_HEIGHT);
		actionTreeView = new ActionTreeView(this,supplier);

		getItems().add(actionTreeView);
	}

	// for loading a selected sprite
	public ActionRow(int ID, ActionVBox<?> ACVBox, ActionTreeView tv,Supplier<List<AbstractSpriteObject>> supplier) {
		super(ID, ACVBox,supplier);
		setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
		getItems().remove(actionTreeView);
		actionTreeView = tv;
		this.getItems().add(actionTreeView);
	}

	// for loading from xml
	public ActionRow(int ID, ActionVBox<?> ACVBox, List<String> params, Action action,Supplier<List<AbstractSpriteObject>> supplier) {
		super(ID, ACVBox,supplier);
		setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
		actionTreeView = new ActionTreeView(this, params, action,supplier);
		this.getItems().add(actionTreeView);
		this.setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
	}

	/********************** PUBLIC METHODS ***********************/

	/**
	 * getTreeViews--returns the action treeview
	 * @return an engine action that can be directly serialized
	 */
	public ActionTreeView getTreeView() {
		return actionTreeView;
	}

	/**
	 * getAction--returns the action for the treeView
	 * @return--the engine action corresponding to the choices the user made in the treeView
	 */
	public Action getAction() {
		try {
			return actionTreeView.getAction();
		} catch (NullPointerException | NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * reduceTreeView--collapsed the treeview, which is once the user has finished editing the treeview in the window and it gets transferred to the tab
	 */
	public void reduceTreeView() {
		this.getTreeView().getRoot().setExpanded(false);
		this.getTreeView().changeRowTVSize();
	}

	/**
	 * expandTreeView--expands the treeview once the user has decided to create a new action
	 */
	public void expandTreeView() {
		this.getTreeView().getRoot().setExpanded(true);
		this.getTreeView().changeRowTVSize();
	}
}
