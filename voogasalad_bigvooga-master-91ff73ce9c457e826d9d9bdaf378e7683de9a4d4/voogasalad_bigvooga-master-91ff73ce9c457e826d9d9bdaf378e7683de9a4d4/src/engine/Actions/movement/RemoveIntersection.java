package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class RemoveIntersection implements Action {

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		if(asking.getLastCollisionChecked() != null)
			asking.setLocation(asking.getLocation().add(asking.getLastCollisionChecked().getOverlapVector()));
	}
	
}
