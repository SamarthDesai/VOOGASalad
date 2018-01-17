package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyHeld implements BooleanOperation {

	private StringOperation check;
	
	public KeyHeld(@VoogaAnnotation(name = "Key", type = VoogaType.STRING) StringOperation check) {
		this.check = check;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getPlayerManager().getKeysDown().contains(check.evaluate(asking, world));
	}
	
}
