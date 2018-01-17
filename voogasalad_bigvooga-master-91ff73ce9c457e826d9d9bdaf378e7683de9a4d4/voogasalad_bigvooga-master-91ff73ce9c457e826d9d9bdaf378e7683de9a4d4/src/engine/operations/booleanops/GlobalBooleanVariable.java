package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;

public class GlobalBooleanVariable implements BooleanOperation {

	private StringOperation varName;

	public GlobalBooleanVariable(@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName) {
		this.varName = varName;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getGlobals().getBoolean(varName.evaluate(asking, world));
	}

}
