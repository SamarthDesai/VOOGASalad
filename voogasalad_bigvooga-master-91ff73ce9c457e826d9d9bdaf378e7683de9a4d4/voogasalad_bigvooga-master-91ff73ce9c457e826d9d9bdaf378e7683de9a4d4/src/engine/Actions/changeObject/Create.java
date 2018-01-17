package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;
import engine.operations.vectorops.VectorOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Create implements Action {

	private StringOperation name;
	private DoubleOperation heading;
	private VectorOperation location;

	public Create(@VoogaAnnotation(name = "Sprite Name", type = VoogaType.STRING) StringOperation name,
			@VoogaAnnotation(name = "Starting Location", type = VoogaType.VECTOR) VectorOperation location,
			@VoogaAnnotation(name = "Starting Heading", type = VoogaType.DOUBLE) DoubleOperation heading) {
		this.name = name;
		this.location = location;
		this.heading = heading;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = world.getGameObject(name.evaluate(asking, world));
		obj.setLocation(location.evaluate(asking, world));
		obj.setHeading(heading.evaluate(asking, world));
		world.addGameObject(obj);
	}

}
