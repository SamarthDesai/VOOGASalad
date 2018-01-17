package engine.archived.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Destroy implements Action {

	private String objectToDestroy;
	
	public Destroy(String objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		world.removeGameObject(world.getWithName(objectToDestroy));
	}
}
