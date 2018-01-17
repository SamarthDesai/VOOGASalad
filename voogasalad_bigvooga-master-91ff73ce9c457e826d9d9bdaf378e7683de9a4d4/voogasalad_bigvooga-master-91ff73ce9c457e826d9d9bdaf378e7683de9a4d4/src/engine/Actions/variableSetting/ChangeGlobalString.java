package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeGlobalString implements Action {

	private StringOperation varName;
	private StringOperation newString;

	public ChangeGlobalString(@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName,
			@VoogaAnnotation(name = "New Value", type = VoogaType.STRING) StringOperation newString) {
		this.varName = varName;
		this.newString = newString;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.getGlobals().setStringVariable(varName.evaluate(asking, world), newString.evaluate(asking, world));
	}

}
