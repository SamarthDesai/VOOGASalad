package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Not implements BooleanOperation {

	private BooleanOperation boolOp;
	
	public Not(@VoogaAnnotation(name = "Boolean", type = VoogaType.BOOLEAN) BooleanOperation condition) {
		this.boolOp = condition;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return !boolOp.evaluate(asking, world);
	}

}
