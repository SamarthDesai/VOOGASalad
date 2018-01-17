package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class VectorDifference implements VectorOperation {
	private VectorOperation first;
	private VectorOperation second;

	public VectorDifference(@VoogaAnnotation(name = "First Vector", type = VoogaType.VECTOR) VectorOperation vector,
			@VoogaAnnotation(name = "Second Vector", type = VoogaType.VECTOR) VectorOperation toSubtract) {
		this.first = vector;
		this.second = toSubtract;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world).subtract(second.evaluate(asking, world));
	}
}
