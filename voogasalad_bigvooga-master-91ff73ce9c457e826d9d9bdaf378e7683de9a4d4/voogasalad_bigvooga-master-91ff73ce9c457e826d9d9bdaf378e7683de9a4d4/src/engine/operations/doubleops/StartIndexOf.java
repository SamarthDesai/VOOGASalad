package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

public class StartIndexOf implements DoubleOperation {

	GameObjectOperation gameObject;
	
	public StartIndexOf(@VoogaAnnotation(name = "Object with Inventory", type = VoogaType.GAMEOBJECT) GameObjectOperation gameObject) {
		this.gameObject = gameObject;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return new Double(gameObject.evaluate(asking, world).getInventory().getStartIndex());
	}

}
