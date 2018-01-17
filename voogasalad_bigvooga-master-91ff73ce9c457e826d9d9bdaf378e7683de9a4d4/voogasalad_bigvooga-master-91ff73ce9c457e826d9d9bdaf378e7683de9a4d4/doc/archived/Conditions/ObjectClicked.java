package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * Checks if an object is initially clicked (thus, can not return true
 * two steps in a row).
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClicked extends Condition {
	
	public ObjectClicked(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		Condition screenClicked = new ScreenClicked(0);
		Condition objectClickHeld = new ObjectClickHeld(0);
		Condition and = new And(0, screenClicked, objectClickHeld);
		return and.isTrue(asking, world);
	}

}
