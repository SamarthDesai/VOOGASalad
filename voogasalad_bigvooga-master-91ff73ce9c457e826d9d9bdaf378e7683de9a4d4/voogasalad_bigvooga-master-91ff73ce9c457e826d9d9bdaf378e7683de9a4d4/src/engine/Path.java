package engine;

import java.util.LinkedList;
import java.util.Queue;

import javafx.geometry.Point2D;

/**
 * Exists as a Queue for points. Objects move towards these points in a FIFO pattern
 * Note: Paths are UNIQUE to objects and are NOT CLONED.
 * @author Nikolas Bramblett
 *
 */
public class Path {
	
	private Queue<Point2D> path;
	private Point2D target;

	public Path() {
		// TODO Auto-generated constructor stub
		path = new LinkedList<Point2D>();
	}
	
	
	public void addPathPoint(Point2D point)
	{
		path.add(point);
	}
	
	public void moveToTarget(GameObject o, double speed) 
	{
		if(target.distance(o.getLocation()) < 1)
		{
			target = path.remove();
		}
		if(path.isEmpty())
		{
			o.setDerivative(1, new Point2D(0,0));
		}
		o.setDerivative(1, o.getLocation().subtract(target).normalize().multiply(speed));
	}

}
