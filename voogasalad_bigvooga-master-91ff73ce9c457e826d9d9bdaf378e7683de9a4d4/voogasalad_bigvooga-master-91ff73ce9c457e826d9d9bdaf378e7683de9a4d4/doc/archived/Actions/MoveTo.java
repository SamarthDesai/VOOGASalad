package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class MoveTo implements Action {

	private double newX;
	private double newY;
	
	public MoveTo(double newX, double newY) {
		this.newX = newX;
		this.newY = newY;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setCoords(newX, newY);
	}

}
