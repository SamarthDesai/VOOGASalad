package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Rotate implements Action {

	private DoubleOperation headingIncrement;
	private GameObjectOperation object;

	public Rotate(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Degrees", type = VoogaType.DOUBLE) DoubleOperation headingIncrement) {
		this.object = object;
		this.headingIncrement = headingIncrement;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = object.evaluate(asking, world);
		obj.setHeading(obj.getHeading() + headingIncrement.evaluate(asking, world));
	}

}
