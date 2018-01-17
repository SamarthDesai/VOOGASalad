package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeString implements Action {

	private String varName;
	private String newString;
	
	public ChangeString(String varName, String newString) {
		this.varName = varName;
		this.newString = newString;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setStringVariable(varName, newString);
	}
	
}
