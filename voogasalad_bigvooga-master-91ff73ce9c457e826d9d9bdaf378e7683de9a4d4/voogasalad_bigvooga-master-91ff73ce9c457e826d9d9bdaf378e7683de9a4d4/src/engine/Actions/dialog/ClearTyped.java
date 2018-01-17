package engine.Actions.dialog;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class ClearTyped implements Action {

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.getPlayerManager().clearTyped();
	}

}
