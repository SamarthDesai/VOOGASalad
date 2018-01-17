package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class RandomDouble implements DoubleOperation {

	@Override
	/**
	 * Returns a random value between 0 (inclusive) and 1 (noninclusive).
	 */
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return Math.random();
	}

}
