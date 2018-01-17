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
public class RotateTo implements Action {

	private DoubleOperation newHeading;
	private GameObjectOperation object;

	public RotateTo(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "New Heading", type = VoogaType.DOUBLE) DoubleOperation newHeading) {
		this.object = object;
		this.newHeading = newHeading;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setHeading(newHeading.evaluate(asking, world));
	}

}
