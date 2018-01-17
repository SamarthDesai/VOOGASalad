package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClicked extends Condition{
	
	public ScreenClicked(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	/**
	 * Returns true if the primary mouse button is down but was not down in the previous step, i.e. when it is clicked.
	 */
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		return !world.getPlayerManager().isPrevPrimaryButtonDown() && world.getPlayerManager().isPrimaryButtonDown();
	}

}
