package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;
import engine.utilities.collisions.BoundingPoint;

/**
 * Checks if click is released from a click on an object. Note that the cursor 
 * can be anywhere for the condition to return true as long as the last click
 * that is being released was on the object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickReleased extends Condition {

	public ObjectClickReleased(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		Condition screenClickReleased = new ScreenClicked(0);
//		return screenClickReleased.isTrue(asking, world) && 
//				world.getPlayerManager().getClickX() > asking.getImage().getX() - 0.5 * asking.getImage().getX() &&
//				world.getPlayerManager().getClickX() < asking.getImage().getX() + 0.5 * asking.getImage().getX() &&
//				world.getPlayerManager().getClickY() > asking.getImage().getY() - 0.5 * asking.getImage().getY() &&
//				world.getPlayerManager().getClickY() < asking.getImage().getY() + 0.5 * asking.getImage().getY();
		return screenClickReleased.isTrue(asking, world) &&
				asking.getImage().checkCollision(new BoundingPoint(world.getPlayerManager().getClickX(), world.getPlayerManager().getClickY())) != null;
	}

}
