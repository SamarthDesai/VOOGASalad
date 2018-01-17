package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.sprite.BoundedImage;
import engine.sprite.Sprite;

/**
 * 
 * @author Nikolas Bramblett, Aaron Paskin
 *
 */
public class Holdable extends TaggableSpriteableVariableContainer {

	private Map<String, List<Action>> useOptions;
	private List<Action> selectActions;

	public Holdable(Sprite sprite) {
		useOptions = new HashMap<String, List<Action>>();
		selectActions = new ArrayList<>();
		this.sprite = sprite;
	}

	public void addUseOption(String useName, List<Action> actions) {
		if (useOptions.containsKey(useName)) {
			useOptions.get(useName).addAll(actions);
		} else
			useOptions.put(useName, actions);
	}

	public void use(String useCase, GameObject keeper, GameObjectEnvironment world) {
		for (Action a : useOptions.get(useCase)) {
			a.execute(keeper, world);
		}
	}

	public List<String> getUses() {
		return new ArrayList<String>(useOptions.keySet());
	}

	public BoundedImage getDisplayable() {
		return sprite.getImage();
	}

	public void setSelectActions(List<Action> selectActions) {
		this.selectActions = selectActions;
	}

	public void select(GameObject keeper, GameObjectEnvironment world) {
		for (Action a : selectActions) {
			a.execute(keeper, world);
		}
	}

	public Holdable clone() {
		Holdable copy = new Holdable(null);
		cloneHelp(copy);

		return copy;
	}

}
