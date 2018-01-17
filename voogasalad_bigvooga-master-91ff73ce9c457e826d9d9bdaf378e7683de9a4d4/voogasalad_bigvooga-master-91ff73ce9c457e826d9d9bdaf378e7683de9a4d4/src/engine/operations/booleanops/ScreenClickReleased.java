package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClickReleased implements BooleanOperation {
	
	public ScreenClickReleased() {}
	
	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getPlayerManager().isPrevPrimaryButtonDown() && !world.getPlayerManager().isPrimaryButtonDown();
	}
	
}
