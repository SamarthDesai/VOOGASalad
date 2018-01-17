package engine.operations.gameobjectops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class CollidedWith implements GameObjectOperation {

	@Override
	public GameObject evaluate(GameObject asking, GameObjectEnvironment world) {
		return asking.getLastCollisionChecked().getGameObject();
	}

}
