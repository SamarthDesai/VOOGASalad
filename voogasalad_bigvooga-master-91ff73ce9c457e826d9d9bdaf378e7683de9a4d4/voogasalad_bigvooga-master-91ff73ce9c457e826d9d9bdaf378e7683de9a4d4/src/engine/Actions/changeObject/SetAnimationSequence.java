package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class SetAnimationSequence implements Action {

	private GameObjectOperation object;
	private StringOperation name;

	public SetAnimationSequence(
			@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Animation Sequence", type = VoogaType.STRING) StringOperation name) {
		this.object = object;
		this.name = name;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).getSprite().setAnimation(name.evaluate(asking, world));
	}

}
