package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.vectorops.VectorOperation;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class AngleBetween implements DoubleOperation {

	private VectorOperation from;
	private VectorOperation to;

	public AngleBetween(@VoogaAnnotation(name = "From", type = VoogaType.VECTOR) VectorOperation from,
			@VoogaAnnotation(name = "To", type = VoogaType.VECTOR) VectorOperation to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		Point2D vector1 = from.evaluate(asking, world);
		Point2D vector2 = to.evaluate(asking, world);
		return Math.toDegrees(Math.atan2(vector2.getY(), vector2.getX()) - Math.atan2(vector1.getY(), vector1.getX()));
	}

}
