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
public class CrossProduct implements DoubleOperation {

	private VectorOperation firstVector;
	private VectorOperation secondVector;

	public CrossProduct(@VoogaAnnotation(name = "First", type = VoogaType.VECTOR) VectorOperation firstVector, @VoogaAnnotation(name = "Second", type = VoogaType.VECTOR) VectorOperation secondVector) {
		this.firstVector = firstVector;
		this.secondVector = secondVector;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return firstVector.evaluate(asking, world).crossProduct(secondVector.evaluate(asking, world)).getZ();
	}
}
