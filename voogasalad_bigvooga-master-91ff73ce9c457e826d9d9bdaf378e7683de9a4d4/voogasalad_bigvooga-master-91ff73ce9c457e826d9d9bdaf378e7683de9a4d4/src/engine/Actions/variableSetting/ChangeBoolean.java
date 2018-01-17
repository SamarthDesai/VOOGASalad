package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.booleanops.BooleanOperation;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeBoolean implements Action {

	private StringOperation varName;
	private BooleanOperation newBooleanOperation;
	private GameObjectOperation object;

	public ChangeBoolean(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName,
			@VoogaAnnotation(name = "New Value", type = VoogaType.BOOLEAN) BooleanOperation newBooleanOperation) {
		this.object = object;
		this.varName = varName;
		this.newBooleanOperation = newBooleanOperation;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setBooleanVariable(varName.evaluate(asking, world),
				newBooleanOperation.evaluate(asking, world));
	}

}
