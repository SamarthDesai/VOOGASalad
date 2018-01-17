package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class And extends Condition {

	private Condition condition1;
	private Condition condition2;
	
	public And(int priorityNum, Condition condition1, Condition condition2) {
		this.priorityNum = priorityNum;
		this.condition1 = condition1;
		this.condition2 = condition2;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		return condition1.isTrue(asking, world) && condition2.isTrue(asking, world);
	}

}
