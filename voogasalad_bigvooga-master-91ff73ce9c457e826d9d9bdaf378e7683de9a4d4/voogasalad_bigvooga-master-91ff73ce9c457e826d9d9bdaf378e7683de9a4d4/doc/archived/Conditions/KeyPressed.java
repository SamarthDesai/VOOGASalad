package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyPressed extends Condition {

	private String check;
	
	public KeyPressed(int priorityNum, String check) {
		this.priorityNum = priorityNum;
		this.check = check;
	}
	
	/**
	 * Returns true when the key named "check" is down but was not down in the previous step, i.e. when it is pressed/tapped
	 */
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		return world.getPlayerManager().getKeysDown().contains(check) && !world.getPlayerManager().getPrevKeysDown().contains(check);
	}
	
}
