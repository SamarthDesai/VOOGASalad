package engine.Actions.dialog;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class DisplayDialog implements Action {

	private StringOperation boxName;
	private VectorOperation location;

	public DisplayDialog(
			@VoogaAnnotation(name = "Dialog Box Name", type = VoogaType.STRING) StringOperation boxName,
			@VoogaAnnotation(name = "Location", type = VoogaType.VECTOR) VectorOperation location) {
		this.boxName = boxName;
		this.location = location;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = world.getGameObject(boxName.evaluate(asking, world));
		obj.setLocation(location.evaluate(asking, world));
		world.addGameObject(obj);
	}

}
