package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyReleased extends Condition {
	
	private String check;
	
	public KeyReleased(int priorityNum, String check) {
		this.priorityNum = priorityNum;
		this.check = check;
	}
	
	/**
	 * Returns true when the key named "check" is not down but was 
	 */
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		return !world.getPlayerManager().getKeysDown().contains(check) && world.getPlayerManager().getPrevKeysDown().contains(check);
	}

}
