package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class Sin implements DoubleOperation {

	private DoubleOperation angle;

	public Sin(@VoogaAnnotation(name = "Degrees", type = VoogaType.DOUBLE) DoubleOperation angle) {
		this.angle = angle;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return Math.sin(Math.toRadians(angle.evaluate(asking, world)));
	}

}
