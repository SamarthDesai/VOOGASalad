package engine.Actions.sizing;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

public class SetWidthCentered implements Action {

	private GameObjectOperation object;
	private DoubleOperation newWidth;

	public SetWidthCentered(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "New Width", type = VoogaType.DOUBLE) DoubleOperation newWidth) {
		this.object = object;
		this.newWidth = newWidth;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = object.evaluate(asking, world);
		obj.setSize(newWidth.evaluate(asking, world), obj.getDimensions().getY());
	}

}
