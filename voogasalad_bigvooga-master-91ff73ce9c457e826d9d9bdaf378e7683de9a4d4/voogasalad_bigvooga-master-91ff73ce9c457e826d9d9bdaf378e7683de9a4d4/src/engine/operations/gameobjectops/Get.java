package engine.operations.gameobjectops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class Get implements GameObjectOperation {

	private GameObject value;
	
	public Get(GameObject value) {
		this.value = value;
	}
	
	@Override
	public GameObject evaluate(GameObject asking, GameObjectEnvironment world) {
		return value;
	}

}
