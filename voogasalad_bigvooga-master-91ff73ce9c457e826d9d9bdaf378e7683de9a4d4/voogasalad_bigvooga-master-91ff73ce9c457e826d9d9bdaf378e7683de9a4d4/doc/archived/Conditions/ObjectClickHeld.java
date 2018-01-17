package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;
import engine.utilities.collisions.BoundingPoint;

/**
 * Checks if a click is being held on an object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickHeld extends Condition {

	public ObjectClickHeld(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		Condition screenClickHeld = new ScreenClickHeld(0);
		return screenClickHeld.isTrue(asking, world) && 
				asking.getImage().checkCollision(new BoundingPoint(world.getPlayerManager().getClickX(), world.getPlayerManager().getClickY())) != null;
	}

}
