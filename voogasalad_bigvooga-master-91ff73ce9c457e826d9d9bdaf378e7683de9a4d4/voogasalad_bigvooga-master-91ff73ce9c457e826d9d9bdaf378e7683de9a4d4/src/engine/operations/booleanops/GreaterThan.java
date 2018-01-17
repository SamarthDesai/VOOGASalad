package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;

public class GreaterThan implements BooleanOperation {

	private DoubleOperation first;
	private DoubleOperation second;

	public GreaterThan(@VoogaAnnotation(name = "First", type = VoogaType.DOUBLE) DoubleOperation first,
			@VoogaAnnotation(name = "Second", type = VoogaType.DOUBLE) DoubleOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world) > second.evaluate(asking, world);
	}

}
