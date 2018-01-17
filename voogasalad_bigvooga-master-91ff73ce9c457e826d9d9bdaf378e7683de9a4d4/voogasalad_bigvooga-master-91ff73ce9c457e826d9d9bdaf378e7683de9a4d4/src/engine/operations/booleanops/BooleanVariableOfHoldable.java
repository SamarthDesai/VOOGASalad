package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.holdableops.HoldableOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class BooleanVariableOfHoldable implements BooleanOperation {

	private StringOperation varName;
	private HoldableOperation holdable;

	public BooleanVariableOfHoldable(@VoogaAnnotation(name = "Holdable", type = VoogaType.HOLDABLE) HoldableOperation holdable,
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName) {
		this.holdable = holdable;
		this.varName = varName;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return holdable.evaluate(asking, world).getBoolean(varName.evaluate(asking, world));
	}

}
