package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * @author Aaron Paskin
 *
 */
public class GlobalDoubleVariable implements DoubleOperation {

	private StringOperation varName;
	private GameObjectOperation object;

	public GlobalDoubleVariable(@VoogaAnnotation(name = "Variable", type = VoogaType.STRING) StringOperation varName) {
		this.varName = varName;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getGlobals().getDouble(varName.evaluate(asking, world));
	}

}
