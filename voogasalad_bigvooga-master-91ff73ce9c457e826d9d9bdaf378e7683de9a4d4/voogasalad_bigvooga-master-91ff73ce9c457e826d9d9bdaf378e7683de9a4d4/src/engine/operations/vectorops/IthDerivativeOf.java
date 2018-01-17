package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;
import javafx.geometry.Point2D;

public class IthDerivativeOf implements VectorOperation {
	private DoubleOperation i;
	private GameObjectOperation obj;

	public IthDerivativeOf(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation obj,
			@VoogaAnnotation(name = "i", type = VoogaType.DOUBLE) DoubleOperation i) {
		this.i = i;
		this.obj = obj;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return obj.evaluate(asking, world).getDerivative((int) Math.round(i.evaluate(asking, world)));
	}
}
