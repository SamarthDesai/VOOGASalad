package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.utilities.collisions.BoundingPoint;

public class ObjectMouseHover implements BooleanOperation {

	GameObjectOperation object;
	public ObjectMouseHover(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation obj) {
		// TODO Auto-generated constructor stub
		object = obj;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		// TODO Auto-generated method stub
		
		return object.evaluate(asking, world).getBounds().checkCollision(new BoundingPoint(world.getAbsoluteMouseCoordinates()))!= null;
	}

}
