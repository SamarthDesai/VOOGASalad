package engine;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for making GameObjects from their originals.
 * 
 * @author Ian Eldridge-Allegra
 */
public class GameObjectFactory {
	Map<String, GameObject> originals;

	public GameObjectFactory() {
		originals = new HashMap<String, GameObject>();
	}

	/**
	 * Adds an object template to the GameObjectFactory, which allows it to be
	 * cloned for future use.
	 */
	public void addBlueprint(GameObject obj) {
		originals.put(obj.getName(), obj.clone());
	}

	public GameObject getInstanceOf(String name) {
		try {
			return originals.get(name).clone();
		} catch (NullPointerException e) {
			throw new VoogaException("NoObjectByName", name);
		}
	}
}
