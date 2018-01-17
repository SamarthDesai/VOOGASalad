package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class StringEquals implements BooleanOperation {

	
	private StringOperation first;
	private StringOperation second;

	public StringEquals(@VoogaAnnotation(name = "First", type = VoogaType.STRING) StringOperation first, @VoogaAnnotation(name = "Second", type = VoogaType.STRING) StringOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world).equals(second.evaluate(asking, world));
	}

}
