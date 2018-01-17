package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class TypedString implements StringOperation {

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getPlayerManager().getTyped();
	}
}
