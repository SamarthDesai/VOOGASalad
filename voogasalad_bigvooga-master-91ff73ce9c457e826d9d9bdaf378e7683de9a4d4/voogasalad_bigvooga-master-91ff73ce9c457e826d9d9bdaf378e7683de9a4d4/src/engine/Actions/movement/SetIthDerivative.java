package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.vectorops.VectorOperation;

public class SetIthDerivative implements Action {

	private VectorOperation vector;
	private DoubleOperation i;
	private GameObjectOperation object;

	public SetIthDerivative(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object, @VoogaAnnotation(name = "Order", type = VoogaType.DOUBLE) DoubleOperation i, @VoogaAnnotation(name = "Vector Derivative", type = VoogaType.VECTOR) VectorOperation vector) {
		this.i = i;
		this.vector = vector;
		this.object = object;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setDerivative((int) Math.round(i.evaluate(asking, world)),
				vector.evaluate(asking, world));
	}
	
}
