package engine.operations.holdableops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.Holdable;
import engine.operations.Operation;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

public class Selected implements HoldableOperation {

	public Selected() {}
	
	@Override
	public Holdable evaluate(GameObject asking, GameObjectEnvironment world) {
		return asking.getInventory().getSelected();
	}

}
