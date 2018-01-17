package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeGlobalDouble implements Action {

	private StringOperation varName;
	private DoubleOperation newDouble;

	public ChangeGlobalDouble(@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName,
			@VoogaAnnotation(name = "New Value", type = VoogaType.DOUBLE) DoubleOperation newDouble) {
		this.varName = varName;
		this.newDouble = newDouble;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.getGlobals().setDoubleVariable(varName.evaluate(asking, world), newDouble.evaluate(asking, world));
	}
}