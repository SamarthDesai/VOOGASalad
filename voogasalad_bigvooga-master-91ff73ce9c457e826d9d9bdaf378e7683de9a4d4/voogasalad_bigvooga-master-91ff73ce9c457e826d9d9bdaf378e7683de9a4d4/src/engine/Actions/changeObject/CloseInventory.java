package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

public class CloseInventory implements Action {

	private GameObjectOperation obj;
	
	public CloseInventory(@VoogaAnnotation(name = "Holder", type = VoogaType.GAMEOBJECT) GameObjectOperation obj) {
		this.obj = obj;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.removeElement(obj.evaluate(asking, world).getInventory());
	}
	
}
