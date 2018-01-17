package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class RemoveHalfIntersection implements Action {

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		asking.setLocation(asking.getLocation().add(asking.getLastCollisionChecked().getOverlapVector().multiply(.5)));
	}
	
}
