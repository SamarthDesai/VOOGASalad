package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;

/**
 * ActionVBox--type of actionconditionVBox that contians the action rows for a sprite object. Purpose: to manage these action rows
 * Assumptions: should be parametrized with <ActionRow> and be a part of actionTab
 * Dependencies: ActionConditionVBox<T> superclass and the ActionVBoxI<T> interface
 * Example of how to use: create in the action tab, edit by accessing rows 
 * @author Owen Smith
 *
 * @param <T>--the type of row, in this case actionRow
 */
public class ActionVBox<T> extends ActionConditionVBox<T> implements ActionVBoxI<T> {
	
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ActionVBox(Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		this.supplier = supplier;
	}
	
	public ActionVBox(List<T> rows,Supplier<List<AbstractSpriteObject>> supplier) {
		super(rows);
		this.supplier = supplier;
	}

	/**
	 * addAction--can add an new action if the user clicks the addAction option. Will create a new row and buildview
	 */
	@Override
	public void addAction() {
		ActionRow actionRow = new ActionRow(getRows().size() + 1, (ActionVBox<ActionRow>) this,supplier);
		addToRows(actionRow);
		new BuildActionView(this, actionRow);
	}
	

}
