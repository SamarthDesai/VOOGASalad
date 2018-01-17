package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.utilities.collisions.BoundingPoint;

/**
 * Checks if click is released from a click on an object. Note that the cursor
 * can be anywhere for the condition to return true as long as the last click
 * that is being released was on the object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickReleased implements BooleanOperation {

	private GameObjectOperation object;

	public ObjectClickReleased(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object) {
		this.object = object;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		BooleanOperation screenClickReleased = new ScreenClickReleased();
		return screenClickReleased.evaluate(asking, world) && object.evaluate(asking, world).getBounds().checkCollision(
				new BoundingPoint(world.getAbsoluteMouseCoordinates())) != null;
	}

}
