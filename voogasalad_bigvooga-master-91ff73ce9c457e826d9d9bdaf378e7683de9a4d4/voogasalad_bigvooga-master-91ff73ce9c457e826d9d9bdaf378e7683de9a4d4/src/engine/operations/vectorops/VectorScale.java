package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class VectorScale implements VectorOperation {

	private VectorOperation vector;
	private DoubleOperation scalar;

	public VectorScale(@VoogaAnnotation(name = "Vector to scale", type = VoogaType.VECTOR) VectorOperation vector,
			@VoogaAnnotation(name = "Scaling Factor", type = VoogaType.DOUBLE) DoubleOperation scalar) {
		this.vector = vector;
		this.scalar = scalar;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return vector.evaluate(asking, world).multiply(scalar.evaluate(asking, world));
	}

}
