package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class XOf implements DoubleOperation {

	private VectorOperation vector;

	public XOf(@VoogaAnnotation(name = "Vector", type = VoogaType.VECTOR) VectorOperation vector) {
		this.vector = vector;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return vector.evaluate(asking, world).getX();
	}

}
