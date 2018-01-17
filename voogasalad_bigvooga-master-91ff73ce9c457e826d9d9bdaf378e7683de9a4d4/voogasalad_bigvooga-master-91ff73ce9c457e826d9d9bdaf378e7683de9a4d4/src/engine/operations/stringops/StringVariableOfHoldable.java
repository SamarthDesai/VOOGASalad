package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.holdableops.HoldableOperation;

/**
 * @author Aaron Paskin
 *
 */
public class StringVariableOfHoldable implements StringOperation {
	private HoldableOperation holdable;
	private StringOperation varName;

	public StringVariableOfHoldable(@VoogaAnnotation(name = "Holdable", type = VoogaType.HOLDABLE) HoldableOperation holdable,
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName) {
		this.holdable = holdable;
		this.varName = varName;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return holdable.evaluate(asking, world).getString(varName.evaluate(asking, world));
	}
}