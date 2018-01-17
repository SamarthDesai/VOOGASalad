package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

public class Towards implements VectorOperation {

	private VectorOperation target;
	private DoubleOperation length;

	public Towards(@VoogaAnnotation(name = "Target Location", type = VoogaType.VECTOR) VectorOperation target,
			@VoogaAnnotation(name = "Length", type = VoogaType.DOUBLE) DoubleOperation length) {
		this.target = target;
		this.length = length;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return target.evaluate(asking, world).subtract(asking.getLocation()).normalize()
				.multiply(length.evaluate(asking, world));
	}

}
