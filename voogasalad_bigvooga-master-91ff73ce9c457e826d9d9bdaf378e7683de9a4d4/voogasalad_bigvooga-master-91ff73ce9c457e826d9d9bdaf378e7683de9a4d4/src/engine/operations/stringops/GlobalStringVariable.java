package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * @author Aaron Paskin
 *
 */
public class GlobalStringVariable implements StringOperation {
	private StringOperation varName;

	public GlobalStringVariable(@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName) {
		this.varName = varName;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getGlobals().getString(varName.evaluate(asking, world));
	}
}
