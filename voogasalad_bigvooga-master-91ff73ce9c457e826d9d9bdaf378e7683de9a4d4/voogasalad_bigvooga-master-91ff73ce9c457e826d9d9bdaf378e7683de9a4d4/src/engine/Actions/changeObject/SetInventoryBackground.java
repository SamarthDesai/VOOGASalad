package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class SetInventoryBackground implements Action {

	private GameObjectOperation target, template;

	public SetInventoryBackground(
			@VoogaAnnotation(name = "Target Object", type = VoogaType.GAMEOBJECT) GameObjectOperation target,
			@VoogaAnnotation(name = "Sprite to be used as template", type = VoogaType.GAMEOBJECT) GameObjectOperation template) {
		this.target = target;
		this.template = template;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		target.evaluate(asking, world).getInventory().setPane(template.evaluate(asking, world).getBounds().clone());

	}

}
