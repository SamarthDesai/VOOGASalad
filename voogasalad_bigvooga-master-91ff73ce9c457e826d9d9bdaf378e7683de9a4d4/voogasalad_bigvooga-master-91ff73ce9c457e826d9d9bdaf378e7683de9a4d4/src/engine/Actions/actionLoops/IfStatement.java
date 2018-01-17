package engine.Actions.actionLoops;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.booleanops.BooleanOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class IfStatement implements Action{
	private BooleanOperation bool;
	private Action action;

	public IfStatement(@VoogaAnnotation(name = "Condition", type = VoogaType.BOOLEAN) BooleanOperation bool,
			@VoogaAnnotation(name = "Action", type = VoogaType.ACTION) Action action) {
		this.bool = bool;
		this.action = action;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		if(bool.evaluate(asking, world))
			action.execute(asking, world);
	}
}
