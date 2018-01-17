package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.vectorops.VectorOperation;
import javafx.geometry.Point2D;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Move implements Action {

	private VectorOperation increment;
	private GameObjectOperation object;

	public Move(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Position Change", type = VoogaType.VECTOR) VectorOperation increment) {
		this.object = object;
		this.increment = increment;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		Point2D vector = increment.evaluate(asking, world);
		GameObject obj = object.evaluate(asking, world);
		obj.setLocation(obj.getX() + vector.getX(), obj.getY() + vector.getY());
	}

}
