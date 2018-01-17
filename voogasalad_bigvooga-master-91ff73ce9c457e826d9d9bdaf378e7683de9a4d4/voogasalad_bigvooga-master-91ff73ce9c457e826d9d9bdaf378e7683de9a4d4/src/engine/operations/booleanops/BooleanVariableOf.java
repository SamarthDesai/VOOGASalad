package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class BooleanVariableOf implements BooleanOperation {

	private StringOperation varName;
	private GameObjectOperation object;

	public BooleanVariableOf(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName) {
		this.object = object;
		this.varName = varName;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return object.evaluate(asking, world).getBoolean(varName.evaluate(asking, world));
	}

}
