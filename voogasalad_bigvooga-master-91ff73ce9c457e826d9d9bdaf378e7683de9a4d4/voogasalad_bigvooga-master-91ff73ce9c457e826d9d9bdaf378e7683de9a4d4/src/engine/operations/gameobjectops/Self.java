package engine.operations.gameobjectops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class Self implements GameObjectOperation {

	public Self() {}
	
	@Override
	public GameObject evaluate(GameObject asking, GameObjectEnvironment world) {
		return asking;
	}
}
