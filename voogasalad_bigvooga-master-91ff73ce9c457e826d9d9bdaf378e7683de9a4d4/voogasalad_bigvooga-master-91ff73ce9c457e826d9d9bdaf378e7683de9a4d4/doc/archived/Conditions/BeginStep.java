package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class BeginStep extends Condition {

	public BeginStep() {
		priorityNum = 0;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		return true;
	}

}
