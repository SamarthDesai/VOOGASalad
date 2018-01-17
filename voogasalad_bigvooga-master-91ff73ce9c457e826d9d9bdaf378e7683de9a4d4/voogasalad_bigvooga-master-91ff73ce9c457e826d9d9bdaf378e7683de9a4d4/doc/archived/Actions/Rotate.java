package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Rotate implements Action {

	private double headingIncrement;
	
	public Rotate(double headingIncrement) {
		this.headingIncrement = headingIncrement;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		GameObject obj = (GameObject)asking;
		obj.setHeading(obj.getHeading() + headingIncrement);
	}

}
