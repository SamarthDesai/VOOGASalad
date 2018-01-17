package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * Plays animation once and then returns to original animation.
 * @author Nikolas Bramblett
 *
 */
public class SetUnloopedAnimation implements Action{

	private GameObjectOperation obj;
	private StringOperation animationName;
	public SetUnloopedAnimation(
			@VoogaAnnotation(name = "Object Name", type = VoogaType.GAMEOBJECT)  GameObjectOperation obj, 
			@VoogaAnnotation(name = "Animation Name", type = VoogaType.STRING) StringOperation animationName) {
		// TODO Auto-generated constructor stub
		this.obj = obj;
		this.animationName = animationName;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		// TODO Auto-generated method stub
		obj.evaluate(asking, world).getSprite().playOnce(animationName.evaluate(asking, world));
	}

}
