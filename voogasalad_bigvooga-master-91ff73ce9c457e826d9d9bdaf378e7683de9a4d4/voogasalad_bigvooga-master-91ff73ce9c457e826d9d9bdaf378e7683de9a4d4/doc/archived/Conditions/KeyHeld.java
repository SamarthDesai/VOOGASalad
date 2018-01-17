package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyHeld extends Condition {

	private String check;
	
	public KeyHeld(int priorityNum, String check) {
		this.priorityNum = priorityNum;
		this.check = check;
	}
	
	
	/**
	 * Returns true when the key named "check" is down
	 */
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		//TODO make inputmanager (explicitly or implicitly)
		return world.getPlayerManager().getKeysDown().contains(check);
	}
	
}
