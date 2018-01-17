package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.booleanops.BooleanOperation;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class TransferObjectToWorld implements Action {

	private GameObjectOperation gameObject;
	private StringOperation worldName;
	private StringOperation layerName;

	private BooleanOperation changeWorld;
	public TransferObjectToWorld(
			@VoogaAnnotation(name = "Object being transferred", type = VoogaType.GAMEOBJECT) GameObjectOperation gameObject,
			@VoogaAnnotation(name = "Name of New World", type = VoogaType.STRING) StringOperation worldName,
			@VoogaAnnotation(name = "Name of New World's Layer", type = VoogaType.STRING) StringOperation layerName,
			@VoogaAnnotation(name = "Move to new World?", type = VoogaType.BOOLEAN) BooleanOperation changeWorld) {
		this.gameObject = gameObject;
		this.worldName = worldName;
		this.layerName = layerName;
		this.changeWorld = changeWorld;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.transfer(gameObject.evaluate(asking, world), worldName.evaluate(asking, world), layerName.evaluate(asking, world));
		if(changeWorld.evaluate(asking, world))
		{
			(new ChangeWorld(worldName)).execute(asking, world);
		}
	}

}
