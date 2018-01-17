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
public class ChangeGlobalBoolean implements Action {

	private StringOperation varName;
	private BooleanOperation newBooleanOperation;

	public ChangeGlobalBoolean(@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName,
			@VoogaAnnotation(name = "New Value", type = VoogaType.BOOLEAN) BooleanOperation newBooleanOperation) {
		this.varName = varName;
		this.newBooleanOperation = newBooleanOperation;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.getGlobals().setBooleanVariable(varName.evaluate(asking, world), newBooleanOperation.evaluate(asking, world));
	}

}
