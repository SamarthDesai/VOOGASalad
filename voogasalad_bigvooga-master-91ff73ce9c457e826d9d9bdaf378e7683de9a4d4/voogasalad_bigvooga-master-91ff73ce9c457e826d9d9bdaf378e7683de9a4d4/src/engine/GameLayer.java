package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 
 * @author Nikolas Bramblett, ...
 *
 */
public class GameLayer implements Layer {

	private final static String DEFAULT_NAME = "llayer";
	public final static String PLAYER_TAG = "Player";

	private String layerName;
	private List<Element> worldElements;
	private List<GameObject> objects = new ArrayList<>();
	private Map<Integer, List<GameObject>> conditionPriorities = new ConcurrentSkipListMap<>();
	private Map<String, GameObject> idToGameObject = new HashMap<>();
	private boolean isTracked;

	public GameLayer() {
		this(DEFAULT_NAME);
	}

	public GameLayer(String name) {
		layerName = name;
		worldElements = new ArrayList<>();
		isTracked = true;
	}

	public void addGameObject(GameObject obj) {
		addElement(obj);
		objects.add(obj);
		idToGameObject.put(obj.getUniqueID(), obj);
		for (Integer i : obj.getPriorities()) {
			if (conditionPriorities.containsKey(i)) {
				conditionPriorities.get(i).add(obj);
			} else {
				List<GameObject> objects = new ArrayList<>();
				objects.add(obj);
				conditionPriorities.put(i, objects);
			}
		}
	}

	@Override
	public void addElement(Element obj) {
		worldElements.add(obj);
	}

	@Override
	public void addElements(List<Element> obj) {
		for (Element o : obj) {
			addElement(o);
		}
	}

	@Override
	public void removeGameObject(GameObject obj) {
		removeElement(obj);
		objects.remove(obj);
		idToGameObject.remove(obj.getUniqueID());
		for (Integer i : obj.getPriorities()) {
			conditionPriorities.get(i).remove(obj);
			if (conditionPriorities.get(i).isEmpty()) {
				conditionPriorities.remove(i);
			}
		}
	}

	@Override
	public void removeElement(Element obj) {
		worldElements.remove(obj);
	}

	@Override
	public void removeElements(List<Element> obj) {
		for (Element o : obj) {
			removeElement(o);
		}
	}

	@Override
	public List<GameObject> getObjectsWithTag(String tag) {
		List<GameObject> tempList = new ArrayList<>();
		for (GameObject o : objects) {
			if (o.is(tag))
				tempList.add(o);
		}
		return tempList;
	}

	public GameObject getByID(String id) {
		return idToGameObject.get(id);
	}

	public boolean isNamed(String tag) {
		return layerName.equals(tag);
	}

	public void step(ConcreteGameObjectEnvironment environment) {
		List<Runnable> runnables = new ArrayList<>();
		environment.setLayer(this);
		for (Element obj : worldElements) {
			obj.step(environment);
		}
		for (Integer i : conditionPriorities.keySet()) {
			for (GameObject obj : conditionPriorities.get(i)) {
				obj.step(i, environment, runnables);
			}
			for (Runnable r : runnables) {
				r.run();
			}
			runnables.clear();
		}
	}

	@Override
	public List<Element> getAllElements() {
		return worldElements;
	}

	@Override
	public GameObject getWithName(String name) {
		for (GameObject go : objects) {
			if (go.getName().equals(name))
				return go;
		}
		throw new RuntimeException("None by name " + name);// TODO
	}

	public List<GameObject> getAllGameObjects() {
		return objects;
	}

	@Override
	public boolean isTracked() {
		return isTracked;
	}
	
	@Override
	public void setIsTracked(boolean isTracked) {
		this.isTracked = isTracked;
	}

}
