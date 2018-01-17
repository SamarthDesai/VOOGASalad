package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.holdableops.HoldableOperation;
import engine.operations.stringops.StringOperation;

/**
 * @author Aaron Paskin
 *
 */
public class DoubleVariableOfHoldable implements DoubleOperation {

	private StringOperation varName;
	private HoldableOperation holdable;

	public DoubleVariableOfHoldable(@VoogaAnnotation(name = "Holdable", type = VoogaType.HOLDABLE) HoldableOperation holdable,
			@VoogaAnnotation(name = "Variable", type = VoogaType.STRING) StringOperation varName) {
		this.holdable = holdable;
		this.varName = varName;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return holdable.evaluate(asking, world).getDouble(varName.evaluate(asking, world));
	}

}
