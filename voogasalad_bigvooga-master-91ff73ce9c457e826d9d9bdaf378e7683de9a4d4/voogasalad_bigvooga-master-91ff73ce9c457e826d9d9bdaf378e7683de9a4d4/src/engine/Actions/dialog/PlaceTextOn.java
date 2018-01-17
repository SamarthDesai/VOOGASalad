package engine.Actions.dialog;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class PlaceTextOn implements Action {

	private GameObjectOperation object;
	private StringOperation text;

	public PlaceTextOn(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object,
			@VoogaAnnotation(name = "Text", type = VoogaType.STRING) StringOperation text) {
		this.object = object;
		this.text = text;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setDialogue(text.evaluate(asking, world));
	}

}
