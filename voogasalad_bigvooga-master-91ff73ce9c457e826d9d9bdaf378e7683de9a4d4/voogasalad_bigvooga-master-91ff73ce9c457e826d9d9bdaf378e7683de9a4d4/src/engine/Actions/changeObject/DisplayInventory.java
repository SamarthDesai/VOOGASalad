package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

public class DisplayInventory implements Action {

	private GameObjectOperation obj;

	public DisplayInventory(@VoogaAnnotation(name = "Holder", type = VoogaType.GAMEOBJECT) GameObjectOperation obj) {
		this.obj = obj;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.addElement(obj.evaluate(asking, world).getInventory());
	}

}
