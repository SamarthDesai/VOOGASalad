package engine.Actions.dialog;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;

public class CloseDialog implements Action{

	private StringOperation boxName;

	public CloseDialog(
			@VoogaAnnotation(name = "Dialog Box Name", type = VoogaType.STRING) StringOperation boxName) {
		this.boxName = boxName;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = world.getWithName(boxName.evaluate(asking, world));
		world.removeGameObject(obj);
	}
	
}
