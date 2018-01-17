package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class MoveByVariable implements Action {

	String xSpeedVar, ySpeedVar;
	public MoveByVariable(String xSpeedVar, String ySpeedVar) {
		this.xSpeedVar = xSpeedVar;
		this.ySpeedVar = ySpeedVar;
	}

	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setCoords(asking.getX() + asking.getDouble(xSpeedVar), asking.getY() + asking.getDouble(ySpeedVar));
	}

}
