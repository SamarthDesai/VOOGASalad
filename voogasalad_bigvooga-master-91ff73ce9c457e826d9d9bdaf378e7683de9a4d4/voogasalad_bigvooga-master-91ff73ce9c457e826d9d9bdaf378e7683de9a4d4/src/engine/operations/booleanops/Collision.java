package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.utilities.collisions.CollisionEvent;
import javafx.geometry.Point2D;

public class Collision implements BooleanOperation {

	private GameObjectOperation first;
	private GameObjectOperation second;

	public Collision(@VoogaAnnotation(name = "First Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation first,
			@VoogaAnnotation(name = "Second Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		GameObject obj1 = first.evaluate(asking, world);
		GameObject obj2 = second.evaluate(asking, world);
		if(obj1 == null || obj2 == null) return false;
		Point2D intersectionVector = obj1.getBounds().checkCollision(obj2.getBounds());
		if (intersectionVector != null) {
			obj1.setLastCollisionChecked(new CollisionEvent(obj2, intersectionVector.multiply(-1)));
			obj2.setLastCollisionChecked(new CollisionEvent(obj1, intersectionVector));
			return true;
		}
		return false;
	}
}
