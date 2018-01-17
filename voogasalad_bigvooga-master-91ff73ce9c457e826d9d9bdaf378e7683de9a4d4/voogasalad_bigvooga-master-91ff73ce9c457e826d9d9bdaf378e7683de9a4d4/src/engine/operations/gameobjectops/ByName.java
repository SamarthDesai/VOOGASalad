package engine.operations.gameobjectops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;

public class ByName implements GameObjectOperation {

	private StringOperation name;

	public ByName(@VoogaAnnotation(name = "Name", type = VoogaType.OBJECTNAME) StringOperation name) {
		this.name = name;
	}

	@Override
	public GameObject evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getWithName(name.evaluate(asking, world));
	}
}
