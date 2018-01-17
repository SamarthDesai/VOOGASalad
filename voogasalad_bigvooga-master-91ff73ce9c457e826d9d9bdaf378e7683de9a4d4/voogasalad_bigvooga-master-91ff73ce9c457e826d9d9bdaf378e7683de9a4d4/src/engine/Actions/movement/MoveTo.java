package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.vectorops.VectorOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class MoveTo implements Action {

	private VectorOperation newLocation;
	private GameObjectOperation object;

	public MoveTo(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "New Location", type = VoogaType.VECTOR) VectorOperation newLocation) {
		this.object = object;
		this.newLocation = newLocation;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setLocation(newLocation.evaluate(asking, world));
	}

}
