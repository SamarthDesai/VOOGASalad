package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;


/**
 * Currently non-functional.
 * 
 * @author aaronpaskin
 * 
 */
public class ChangeWorld implements Action {

	private StringOperation nameOfWorld;

	public ChangeWorld(@VoogaAnnotation(name = "World Name", type = VoogaType.STRING) StringOperation nameOfNewWorld) {
		this.nameOfWorld = nameOfNewWorld;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.setNextWorld(nameOfWorld.evaluate(asking, world));
	}

}
