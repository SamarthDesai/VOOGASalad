package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClicked implements BooleanOperation {
	
	public ScreenClicked() {}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return !world.getPlayerManager().isPrevPrimaryButtonDown() && world.getPlayerManager().isPrimaryButtonDown();
	}

}
