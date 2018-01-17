package engine.operations.gameobjectops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;

public class GetByUniqueID implements GameObjectOperation {
	private StringOperation uniqueID;

	public GetByUniqueID(@VoogaAnnotation(name = "Unique ID", type = VoogaType.STRING) StringOperation uniqueID) {
		this.uniqueID = uniqueID;
	}

	@Override
	public GameObject evaluate(GameObject asking, GameObjectEnvironment world) {
		return world.getByID(uniqueID.evaluate(asking, world));
	}
}
