package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class RemoveFromWorld implements Action {

	private GameObjectOperation objectToDestroy;
	
	public RemoveFromWorld(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.removeGameObject(objectToDestroy.evaluate(asking, world));
	}
}
