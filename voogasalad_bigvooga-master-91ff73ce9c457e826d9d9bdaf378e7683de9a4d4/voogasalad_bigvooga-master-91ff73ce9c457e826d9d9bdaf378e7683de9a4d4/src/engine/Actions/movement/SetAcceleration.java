package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class SetAcceleration implements Action {

	private GameObjectOperation object;
	private VectorOperation acceleration;

	public SetAcceleration(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "New Acceleration (px/step^2)", type = VoogaType.VECTOR) VectorOperation acceleration) {
		this.object = object;
		this.acceleration = acceleration;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setDerivative(2, acceleration.evaluate(asking, world));
	}
}
