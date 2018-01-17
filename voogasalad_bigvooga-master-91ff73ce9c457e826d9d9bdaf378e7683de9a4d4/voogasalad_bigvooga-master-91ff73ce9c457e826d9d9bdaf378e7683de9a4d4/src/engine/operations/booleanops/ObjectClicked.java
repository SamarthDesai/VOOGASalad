package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * Checks if an object is initially clicked (thus, can not return true
 * two steps in a row).
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClicked implements BooleanOperation {
	
	private BooleanOperation operation;

	public ObjectClicked(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object) {
		BooleanOperation screenClicked = new ScreenClicked();
		BooleanOperation objectClickHeld = new ObjectClickHeld(object);
		operation = new And(screenClicked, objectClickHeld);
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return operation.evaluate(asking, world);
	}

}
