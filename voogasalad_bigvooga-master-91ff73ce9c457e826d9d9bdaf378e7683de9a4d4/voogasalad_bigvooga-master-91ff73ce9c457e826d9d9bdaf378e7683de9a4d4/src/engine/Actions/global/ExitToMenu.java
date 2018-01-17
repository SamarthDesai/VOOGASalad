package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;

public class ExitToMenu implements Action {

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.exitToMenu();
	}

}
