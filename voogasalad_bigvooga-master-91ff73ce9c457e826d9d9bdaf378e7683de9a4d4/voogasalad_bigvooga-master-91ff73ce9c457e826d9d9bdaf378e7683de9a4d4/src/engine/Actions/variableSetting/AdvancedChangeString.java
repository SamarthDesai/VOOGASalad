package engine.Actions.variableSetting;

import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class AdvancedChangeString extends ChangeString {

	public AdvancedChangeString(
			@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING) StringOperation varName,
			@VoogaAnnotation(name = "New Value", type = VoogaType.STRING) StringOperation newString) {
		super(object, varName, newString);
	}

}
