package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class NormalToCollision implements VectorOperation {

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		if(asking.getLastCollisionChecked() != null)
			return asking.getLastCollisionChecked().getOverlapVector();
		return new Point2D(0,0);
	}

}
