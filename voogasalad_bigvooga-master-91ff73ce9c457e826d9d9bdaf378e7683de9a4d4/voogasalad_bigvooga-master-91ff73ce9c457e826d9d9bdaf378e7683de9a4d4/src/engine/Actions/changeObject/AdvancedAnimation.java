package engine.Actions.changeObject;

import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class AdvancedAnimation extends SetAnimationSequence{

	public AdvancedAnimation(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Animation Sequence", type = VoogaType.STRING) StringOperation name) {
		super(object, name);
		// TODO Auto-generated constructor stub
	}

	

}
