package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class DoubleEquals implements BooleanOperation {

	private static final double ERROR = 0.001;
	private DoubleOperation first;
	private DoubleOperation second;

	public DoubleEquals(@VoogaAnnotation(name = "First", type = VoogaType.DOUBLE) DoubleOperation first,
			@VoogaAnnotation(name = "Second", type = VoogaType.DOUBLE) DoubleOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return Math.abs(first.evaluate(asking, world) - second.evaluate(asking, world)) <= ERROR;
	}

}
