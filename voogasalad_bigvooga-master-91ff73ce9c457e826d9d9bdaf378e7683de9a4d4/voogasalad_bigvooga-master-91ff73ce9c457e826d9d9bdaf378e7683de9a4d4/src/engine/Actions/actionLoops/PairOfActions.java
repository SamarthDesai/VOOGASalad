package engine.Actions.actionLoops;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class PairOfActions implements Action {

	private Action first;
	private Action second;

	public PairOfActions(@VoogaAnnotation(name = "First Action", type = VoogaType.ACTION) Action first,
			@VoogaAnnotation(name = "Second Action", type = VoogaType.ACTION) Action second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		first.execute(asking, world);
		second.execute(asking, world);
	}

}
