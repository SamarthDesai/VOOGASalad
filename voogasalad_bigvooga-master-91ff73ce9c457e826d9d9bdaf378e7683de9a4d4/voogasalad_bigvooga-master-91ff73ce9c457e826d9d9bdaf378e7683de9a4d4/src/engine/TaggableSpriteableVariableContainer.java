package engine;

import java.util.HashSet;
import java.util.Set;

import engine.sprite.Sprite;

/**
 * 
 * @author Aaron Paskin
 *
 */
public abstract class TaggableSpriteableVariableContainer extends VariableContainer {

	protected Sprite sprite;
	protected Set<String> tagSet;
	
	public Set<String> getTags() {
		return new HashSet<String>(tagSet);
	}

	public void setSprite(Sprite set) {
		sprite = set;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void cloneHelp(TaggableSpriteableVariableContainer copy) {
		for (String tag : tagSet)
			copy.addTag(tag);
		for (String var : stringVars.keySet())
			copy.setStringVariable(var, stringVars.get(var));
		for (String var : doubleVars.keySet())
			copy.setDoubleVariable(var, doubleVars.get(var));
		for (String var : booleanVars.keySet())
			copy.setBooleanVariable(var, booleanVars.get(var));

		copy.sprite = sprite.clone();
		copy.tagSet = new HashSet<String>(tagSet);
	}

	public void addTag(String tag) {
		tagSet.add(tag);
	}

	public boolean is(String tag) {
		return tagSet.contains(tag);
	}
	
}
