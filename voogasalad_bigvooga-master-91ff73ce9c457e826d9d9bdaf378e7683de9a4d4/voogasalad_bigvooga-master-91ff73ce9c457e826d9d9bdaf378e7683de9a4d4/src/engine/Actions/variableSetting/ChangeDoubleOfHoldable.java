package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.holdableops.HoldableOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeDoubleOfHoldable implements Action {
	
	private HoldableOperation holdable;
	private StringOperation varName;
	private DoubleOperation newDoubleOperation;

	public ChangeDoubleOfHoldable(@VoogaAnnotation(name = "Holdable", type = VoogaType.HOLDABLE)HoldableOperation holdable, 
									@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING)StringOperation varName, 
									@VoogaAnnotation(name = "New Value", type = VoogaType.DOUBLE)DoubleOperation newDoubleOperation) {
		this.holdable = holdable;
		this.varName = varName;
		this.newDoubleOperation = newDoubleOperation;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		holdable.evaluate(asking, world).setDoubleVariable(varName.evaluate(asking, world), newDoubleOperation.evaluate(asking, world));
	}

}
