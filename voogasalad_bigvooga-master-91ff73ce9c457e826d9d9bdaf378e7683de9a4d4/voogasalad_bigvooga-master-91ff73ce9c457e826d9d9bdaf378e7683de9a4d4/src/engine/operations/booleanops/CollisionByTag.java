package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.Get;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class CollisionByTag implements BooleanOperation {

	private StringOperation tag;
	
	public CollisionByTag(@VoogaAnnotation(name = "Tag", type = VoogaType.STRING) StringOperation tag) {
		this.tag = tag;
	}

	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		for(GameObject g : world.getObjectsWithTag(tag.evaluate(asking, world))) {
			if(g == asking)
				continue;
			boolean result = new Collision(new Get(asking), new Get(g)).evaluate(asking, world);
			if(result)
				return true;
		}
		return false;
	}
}
