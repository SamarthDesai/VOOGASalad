package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

public class HeadingOf implements DoubleOperation {

	private GameObjectOperation object;

	public HeadingOf(@VoogaAnnotation(name = "Object", type = VoogaType.GAMEOBJECT) GameObjectOperation object) {
		this.object = object;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return object.evaluate(asking, world).getHeading();
	}

}
