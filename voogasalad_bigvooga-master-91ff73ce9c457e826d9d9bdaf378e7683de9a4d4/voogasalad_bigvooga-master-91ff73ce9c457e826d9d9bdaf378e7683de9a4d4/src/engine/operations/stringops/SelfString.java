package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class SelfString implements StringOperation{

	private String stored;
	public SelfString(String s) {
		stored = s;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return stored;
	}

}
