package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.stringops.StringOperation;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.holdableops.HoldableOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeStringOfHoldable implements Action {
	
	private HoldableOperation holdable;
	private StringOperation varName;
	private StringOperation newStringOperation;

	public ChangeStringOfHoldable(@VoogaAnnotation(name = "Holdable", type = VoogaType.HOLDABLE)HoldableOperation holdable, 
									@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING)StringOperation varName, 
									@VoogaAnnotation(name = "New Value", type = VoogaType.STRING)StringOperation newStringOperation) {
		this.holdable = holdable;
		this.varName = varName;
		this.newStringOperation = newStringOperation;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		holdable.evaluate(asking, world).setStringVariable(varName.evaluate(asking, world), newStringOperation.evaluate(asking, world));
	}

}
