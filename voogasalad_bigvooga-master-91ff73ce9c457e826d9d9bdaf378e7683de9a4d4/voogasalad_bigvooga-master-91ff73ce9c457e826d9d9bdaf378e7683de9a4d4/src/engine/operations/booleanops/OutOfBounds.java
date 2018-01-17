package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

public class OutOfBounds implements BooleanOperation {

	private GameObjectOperation obj;
	public OutOfBounds(@VoogaAnnotation(name = "Sprite Name", type = VoogaType.GAMEOBJECT) GameObjectOperation obj) {
		// TODO Auto-generated constructor stub
		this.obj = obj;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		// TODO Auto-generated method stub
		
		return world.inBounds(obj.evaluate(asking, world));
	}

}
