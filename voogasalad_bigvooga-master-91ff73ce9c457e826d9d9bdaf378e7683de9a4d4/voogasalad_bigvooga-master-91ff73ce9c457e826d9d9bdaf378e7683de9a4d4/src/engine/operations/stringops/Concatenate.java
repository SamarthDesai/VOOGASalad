package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Concatenate implements StringOperation {

	private StringOperation first;
	private StringOperation second;

	public Concatenate(@VoogaAnnotation(name = "Start", type = VoogaType.STRING) StringOperation first,
			@VoogaAnnotation(name = "End", type = VoogaType.STRING) StringOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world) + second.evaluate(asking, world);
	}
}
