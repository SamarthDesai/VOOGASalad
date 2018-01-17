package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class UnitVector implements VectorOperation {

	private VectorOperation vector;

	public UnitVector(@VoogaAnnotation(name = "Vector", type = VoogaType.VECTOR) VectorOperation vector) {
		this.vector = vector;
	}
	
	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return vector.evaluate(asking, world).normalize();
	}

}
