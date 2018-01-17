package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.holdableops.HoldableOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class RemoveFromInventory implements Action {

	private GameObjectOperation holder;
	private HoldableOperation holdable;
	
	public RemoveFromInventory(@VoogaAnnotation(name = "Holder", type = VoogaType.GAMEOBJECT)GameObjectOperation holder,
								@VoogaAnnotation(name = "Holdable To Transfer", type = VoogaType.HOLDABLE)HoldableOperation holdable) {
		this.holder = holder;
		this.holdable = holdable;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		holder.evaluate(asking, world).getInventory().removeObject(holdable.evaluate(asking, world));
	}
	
}

	
