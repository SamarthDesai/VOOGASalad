package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class DoubleVariableOf implements DoubleOperation {

	private StringOperation varName;
	private GameObjectOperation object;

	public DoubleVariableOf(@VoogaAnnotation(name = "Object", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Variable", type = VoogaType.STRING) StringOperation varName) {
		this.object = object;
		this.varName = varName;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return object.evaluate(asking, world).getDouble(varName.evaluate(asking, world));
	}

}
