package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Create implements Action {

	private String name;
	double x, y, heading;
	public Create(String name, double x, double y, double heading) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.heading = heading;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		
		world.addGameObject(name, x , y, heading);
	}
	
}
