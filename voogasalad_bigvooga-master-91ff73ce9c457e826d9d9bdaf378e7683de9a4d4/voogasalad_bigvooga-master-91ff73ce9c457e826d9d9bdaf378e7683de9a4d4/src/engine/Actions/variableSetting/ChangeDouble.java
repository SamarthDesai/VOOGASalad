package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeDouble implements Action {

	private StringOperation varName;
	private DoubleOperation newDouble;
	private GameObjectOperation object;

	public ChangeDouble(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName,
			@VoogaAnnotation(name = "New Value", type = VoogaType.DOUBLE) DoubleOperation newDouble) {
		this.object = object;
		this.varName = varName;
		this.newDouble = newDouble;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setDoubleVariable(varName.evaluate(asking, world),
				newDouble.evaluate(asking, world));
	}
}