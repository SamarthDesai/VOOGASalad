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

public class Nearest implements GameObjectOperation{

	private StringOperation tag;
	
	public Nearest(@VoogaAnnotation(name = "Tag", type = VoogaType.STRING) StringOperation tag) {
		this.tag = tag;
	}
	
	@Override
	public GameObject evaluate(GameObject asking, GameObjectEnvironment world) {
		List<GameObject> otherObjects = world.getObjectsWithTag(tag.evaluate(asking, world));
		double distance = Double.MAX_VALUE;
		GameObject nearest = null;
		for(GameObject obj : otherObjects) {
			//Magnitude of VectorDifference of Location
			LocationOf askingLocOperation = new LocationOf(new Self());
			LocationOf objLocOperation = new LocationOf((a,b) -> (GameObject)obj);
			Magnitude magnitude = new Magnitude(new VectorDifference(askingLocOperation, objLocOperation));
			double objDist = magnitude.evaluate(asking, world);
			if(objDist < distance) {
				distance = objDist;
				nearest = obj;
			}
		}
		return nearest;
	}

}
