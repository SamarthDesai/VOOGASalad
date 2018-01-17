package engine.Actions.sizing;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

public class SetWidthFromLeft implements Action {

	private GameObjectOperation object;
	private DoubleOperation newWidth;

	public SetWidthFromLeft(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "New Width", type = VoogaType.DOUBLE) DoubleOperation newWidth) {
		this.object = object;
		this.newWidth = newWidth;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = object.evaluate(asking, world);
		double width = newWidth.evaluate(asking, world);
		double oldWidth = obj.getDimensions().getX();
		obj.setSize(width, obj.getDimensions().getY());
		obj.setLocation(obj.getX()+(width-oldWidth)/2, obj.getY());
	}

}
