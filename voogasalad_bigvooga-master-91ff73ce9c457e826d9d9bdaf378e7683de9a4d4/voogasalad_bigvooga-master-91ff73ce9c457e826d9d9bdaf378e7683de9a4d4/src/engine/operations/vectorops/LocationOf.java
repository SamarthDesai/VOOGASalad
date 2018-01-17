package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class LocationOf implements VectorOperation {
	private GameObjectOperation gameObject;

	public LocationOf(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation gameObject) {
		this.gameObject = gameObject;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = gameObject.evaluate(asking, world);
		return new Point2D(obj.getX(), obj.getY());
	}
	
}
