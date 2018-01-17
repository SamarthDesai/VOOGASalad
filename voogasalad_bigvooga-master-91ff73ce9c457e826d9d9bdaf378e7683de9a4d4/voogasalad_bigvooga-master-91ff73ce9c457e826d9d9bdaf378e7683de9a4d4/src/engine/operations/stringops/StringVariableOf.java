package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class StringVariableOf implements StringOperation {
	private GameObjectOperation object;
	private StringOperation varName;

	public StringVariableOf(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName) {
		this.object = object;
		this.varName = varName;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return object.evaluate(asking, world).getString(varName.evaluate(asking, world));
	}
}
