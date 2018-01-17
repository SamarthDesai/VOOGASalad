package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

public class Cos implements DoubleOperation {

	private DoubleOperation angle;

	public Cos(@VoogaAnnotation(name = "Degrees", type = VoogaType.DOUBLE) DoubleOperation angle) {
		this.angle = angle;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return Math.cos(Math.toRadians(angle.evaluate(asking, world)));
	}

}
