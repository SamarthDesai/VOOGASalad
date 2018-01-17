package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Bounce implements Action {

	private DoubleOperation k;

	public Bounce(@VoogaAnnotation(name = "Coefficient Of Restitution", type = VoogaType.DOUBLE) DoubleOperation coefficientOfRestitution) {
		k = coefficientOfRestitution;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		if(asking.getLastCollisionChecked() == null)
			return;
		Point2D normal = asking.getLastCollisionChecked().getOverlapVector().normalize();
		Point2D vel = asking.getDerivative(1);
		Point2D projection = normal.multiply(vel.dotProduct(normal));
		asking.setDerivative(1, vel.subtract(projection.multiply(2)).multiply(k.evaluate(asking, world)));
		new RemoveIntersection().execute(asking, world);
	}

}
