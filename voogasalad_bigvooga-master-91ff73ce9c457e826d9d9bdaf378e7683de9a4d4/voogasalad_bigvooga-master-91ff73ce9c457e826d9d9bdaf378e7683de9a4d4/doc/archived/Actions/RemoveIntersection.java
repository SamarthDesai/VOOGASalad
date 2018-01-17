package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import javafx.geometry.Point2D;

/**
 * 
 * @author aaronpaskin
 *
 */
public class RemoveIntersection implements Action {
	
	@Override
	public void execute(GameObject asking, Layer world) {
		Point2D intVect = asking.getLastCollisionChecked().getOverlapVector();
		asking.setCoords(asking.getX()+intVect.getX(), asking.getY()+intVect.getY());
	}
	
}
