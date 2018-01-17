package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

public class BasicVector implements VectorOperation {

	private DoubleOperation x;
	private DoubleOperation y;

	public BasicVector(@VoogaAnnotation(name = "X", type = VoogaType.DOUBLE) DoubleOperation x,
			@VoogaAnnotation(name = "Y", type = VoogaType.DOUBLE) DoubleOperation y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return new Point2D(x.evaluate(asking, world), y.evaluate(asking, world));
	}

}
