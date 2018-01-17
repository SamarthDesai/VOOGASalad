package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class BooleanValue implements BooleanOperation {

	private boolean value;

	public BooleanValue(boolean value) {
		this.value = value;
	}
	
	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return value;
	}

}
