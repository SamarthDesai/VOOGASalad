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
public class DotProduct implements DoubleOperation {

	private VectorOperation firstVector;
	private VectorOperation secondVector;

	public DotProduct(@VoogaAnnotation(name = "First Vector", type = VoogaType.VECTOR) VectorOperation firstVector,
			@VoogaAnnotation(name = "Second Vector", type = VoogaType.VECTOR) VectorOperation secondVector) {
		this.firstVector = firstVector;
		this.secondVector = secondVector;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return firstVector.evaluate(asking, world).dotProduct(secondVector.evaluate(asking, world));
	}

}
