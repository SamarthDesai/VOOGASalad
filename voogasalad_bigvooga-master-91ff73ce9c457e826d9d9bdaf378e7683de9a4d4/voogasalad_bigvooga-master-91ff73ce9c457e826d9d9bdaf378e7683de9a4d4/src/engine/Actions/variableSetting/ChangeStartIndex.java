package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeStartIndex implements Action {

	private DoubleOperation startIndex;
	private GameObjectOperation object;

	public ChangeStartIndex(
			@VoogaAnnotation(name = "New Start Index", type = VoogaType.DOUBLE) DoubleOperation startIndex,
			@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object) {
		this.startIndex = startIndex;
		this.object = object;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		int start = (int) Math.round(startIndex.evaluate(asking, world));
		object.evaluate(asking, world).getInventory().setStartIndex(start);
	}

}
