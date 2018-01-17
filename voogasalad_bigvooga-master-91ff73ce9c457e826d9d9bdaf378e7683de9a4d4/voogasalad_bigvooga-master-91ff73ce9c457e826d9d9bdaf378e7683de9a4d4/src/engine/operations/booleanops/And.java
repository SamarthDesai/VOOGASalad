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
public class And implements BooleanOperation {

	private BooleanOperation boolOp1;
	private BooleanOperation boolOp2;

	public And(@VoogaAnnotation(name = "First", type = VoogaType.BOOLEAN) BooleanOperation boolOp1,
			@VoogaAnnotation(name = "Second", type = VoogaType.BOOLEAN) BooleanOperation boolOp2) {
		this.boolOp1 = boolOp1;
		this.boolOp2 = boolOp2;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return boolOp1.evaluate(asking, world) && boolOp2.evaluate(asking, world);
	}

}
