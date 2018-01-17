package engine.Actions.actionLoops;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;

/**
 * @author aaronpaskin, Ian Eldridge-Allegra
 *
 */
public class DoTimes implements Action {

	private DoubleOperation times;
	private Action action;

	public DoTimes(@VoogaAnnotation(name = "Number of Times", type = VoogaType.DOUBLE) DoubleOperation times,
			@VoogaAnnotation(name = "Action", type = VoogaType.ACTION) Action action) {
		this.times = times;
		this.action = action;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		int num = (int) Math.round(times.evaluate(asking, world));
		for (int i = 0; i < num; i++) {
			action.execute(asking, world);
		}
	}

}
