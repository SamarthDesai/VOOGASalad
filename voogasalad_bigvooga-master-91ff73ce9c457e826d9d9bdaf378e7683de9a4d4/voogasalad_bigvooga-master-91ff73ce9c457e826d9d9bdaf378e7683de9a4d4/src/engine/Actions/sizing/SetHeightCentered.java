package engine.Actions.sizing;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

public class SetHeightCentered implements Action {

	private GameObjectOperation object;
	private DoubleOperation newHeight;

	public SetHeightCentered(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "New Height", type = VoogaType.DOUBLE) DoubleOperation newHeight) {
		this.object = object;
		this.newHeight = newHeight;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = object.evaluate(asking, world);
		obj.setSize(obj.getDimensions().getX(), newHeight.evaluate(asking, world));
	}

}
