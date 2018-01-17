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
public class SetVelocity implements Action {

	private GameObjectOperation object;
	private VectorOperation velocity;

	public SetVelocity(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "New Velocity (px/step)", type = VoogaType.VECTOR) VectorOperation velocity) {
		this.object = object;
		this.velocity = velocity;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setDerivative(1, velocity.evaluate(asking, world));
	}

}
