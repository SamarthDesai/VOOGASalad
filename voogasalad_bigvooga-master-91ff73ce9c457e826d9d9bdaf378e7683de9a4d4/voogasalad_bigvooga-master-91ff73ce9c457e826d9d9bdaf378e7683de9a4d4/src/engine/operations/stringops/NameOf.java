package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

public class NameOf implements StringOperation {

	private GameObjectOperation object;

	public NameOf(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object) {
		this.object = object;
	}
	
	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return object.evaluate(asking, world).getName();
	}

}
