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
public class VectorHeadingOf implements VectorOperation {

	private GameObjectOperation object;

	public VectorHeadingOf(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object) {
		this.object = object;
	}
	
	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return new Point2D(Math.cos(Math.toRadians(object.evaluate(asking, world).getHeading())), Math.sin(Math.toRadians(object.evaluate(asking, world).getHeading())));
	}

}