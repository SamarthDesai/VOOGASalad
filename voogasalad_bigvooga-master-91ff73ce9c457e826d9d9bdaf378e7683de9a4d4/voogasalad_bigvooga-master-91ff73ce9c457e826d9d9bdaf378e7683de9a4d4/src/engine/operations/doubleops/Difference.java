package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Difference implements DoubleOperation {
	private DoubleOperation number;
	private DoubleOperation toSubtract;

	public Difference(@VoogaAnnotation(name = "First", type = VoogaType.DOUBLE) DoubleOperation number,
			@VoogaAnnotation(name = "Second", type = VoogaType.DOUBLE) DoubleOperation toSubtract) {
		this.number = number;
		this.toSubtract = toSubtract;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return number.evaluate(asking, world) - toSubtract.evaluate(asking, world);
	}

}
