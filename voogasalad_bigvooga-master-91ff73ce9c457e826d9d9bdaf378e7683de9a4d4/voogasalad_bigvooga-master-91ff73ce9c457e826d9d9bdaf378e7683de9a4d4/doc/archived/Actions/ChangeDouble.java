package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeDouble implements Action {

	private String varName;
	private double newDouble;
	private boolean relative;
	
	public ChangeDouble(String varName, double newDouble, boolean relative) {
		this.varName = varName;
		this.newDouble = newDouble;
		
		this.relative = relative;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		if(relative)
			asking.setDoubleVariable(varName, asking.getDouble(varName)+newDouble);
		else
			asking.setDoubleVariable(varName, newDouble);
	}
	
}
