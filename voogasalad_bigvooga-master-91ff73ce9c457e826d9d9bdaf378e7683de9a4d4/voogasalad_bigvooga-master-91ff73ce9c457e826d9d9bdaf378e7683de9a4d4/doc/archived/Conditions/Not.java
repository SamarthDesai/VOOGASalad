package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Not extends Condition {

	private Condition condition;
	
	public Not(int priorityNum, Condition condition) {
		this.priorityNum = priorityNum;
		this.condition = condition;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		return !condition.isTrue(asking, world);
	}

}
