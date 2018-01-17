package engine.operations.gameobjectops;

import java.util.List;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.Magnitude;
import engine.operations.stringops.StringOperation;
import engine.operations.vectorops.LocationOf;
import engine.operations.vectorops.VectorDifference;

public class Farthest implements GameObjectOperation{

	private StringOperation tag;
	
	public Farthest(@VoogaAnnotation(name = "Tag", type = VoogaType.STRING) StringOperation tag) {
		this.tag = tag;
	}
	
	@Override
	public GameObject evaluate(GameObject asking, GameObjectEnvironment world) {
		List<GameObject> otherObjects = world.getObjectsWithTag(tag.evaluate(asking, world));
		double distance = Double.MIN_VALUE;
		GameObject nearest = null;
		for(GameObject obj : otherObjects) {
			LocationOf askingLocOperation = new LocationOf(new Self());
			LocationOf objLocOperation = new LocationOf(new Get(obj));
			Magnitude magnitude = new Magnitude(new VectorDifference(askingLocOperation, objLocOperation));
			double objDist = magnitude.evaluate(asking, world);
			if(objDist > distance) {
				distance = objDist;
				nearest = obj;
			}
		}
		return nearest;
	}

}
