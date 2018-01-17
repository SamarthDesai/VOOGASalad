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
public class VectorSum implements VectorOperation {
	private VectorOperation first;
	private VectorOperation second;

	public VectorSum(@VoogaAnnotation(name = "First Vector", type = VoogaType.VECTOR) VectorOperation first,
			@VoogaAnnotation(name = "Second Vector", type = VoogaType.VECTOR) VectorOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world).add(second.evaluate(asking, world));
	}
}
