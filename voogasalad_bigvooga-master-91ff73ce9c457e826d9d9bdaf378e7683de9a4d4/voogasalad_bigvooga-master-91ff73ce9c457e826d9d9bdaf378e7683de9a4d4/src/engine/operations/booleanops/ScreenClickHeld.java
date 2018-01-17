package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClickHeld implements BooleanOperation {
	
	public ScreenClickHeld() {}
	
	/**
	 * Returns true if the primary mouse button is down, regardless of whether or not it was down in the previous step
	 */
	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getPlayerManager().isPrimaryButtonDown();
	}
	
}
