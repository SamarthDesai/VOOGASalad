package exampleCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import engine.GameObject;
import engine.World;

public class DummyWorld implements World {

	private List<GameObject> objs = new ArrayList<GameObject>();
	
	@Override
	public void addGameObject(GameObject obj) {
		objs.add(obj);
	}

	@Override
	public void removeGameObject(GameObject obj) {
		objs.remove(obj);
	}

	@Override
	public List<GameObject> getWithTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<GameObject> iterator() {
		return objs.iterator();
	}

}
