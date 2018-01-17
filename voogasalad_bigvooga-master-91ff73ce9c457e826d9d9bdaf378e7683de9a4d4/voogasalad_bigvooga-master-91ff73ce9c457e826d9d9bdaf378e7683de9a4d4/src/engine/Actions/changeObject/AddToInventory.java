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
public class AddToInventory implements Action {

	private GameObjectOperation giver;
	private GameObjectOperation taker;
	private HoldableOperation holdable;
	
	public AddToInventory(@VoogaAnnotation(name = "Giver", type = VoogaType.GAMEOBJECT)GameObjectOperation giver, 
							@VoogaAnnotation(name = "Taker", type = VoogaType.GAMEOBJECT)GameObjectOperation taker, 
							@VoogaAnnotation(name = "Holdable To Transfer", type = VoogaType.HOLDABLE)HoldableOperation holdable) {
		this.giver = giver;
		this.taker = taker;
		this.holdable = holdable;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		giver.evaluate(asking, world).getInventory().removeObject((holdable.evaluate(asking, world)));
		taker.evaluate(asking, world).getInventory().removeObject(holdable.evaluate(asking, world));
	}
	
}

	
