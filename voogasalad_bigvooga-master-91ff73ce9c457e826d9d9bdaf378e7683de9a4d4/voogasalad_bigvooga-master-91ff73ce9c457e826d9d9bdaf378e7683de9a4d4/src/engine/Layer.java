package engine;
import java.util.List;

public interface Layer {
	
	public void addElement(Element obj);
	public void addGameObject(GameObject obj);
	public void addElements(List<Element> obj);
	public void removeElement(Element obj);
	public void removeElements(List<Element> obj);
	public List<GameObject> getObjectsWithTag(String tag);
	public GameObject getWithName(String name);
	public boolean isNamed(String tag);
	public boolean isTracked();
	public void setIsTracked(boolean isTracked);
	public void step(ConcreteGameObjectEnvironment environment);
	public List<Element> getAllElements();
	void removeGameObject(GameObject obj);
	public List<? extends GameObject> getAllGameObjects();
	public GameObject getByID(String uniqueID);
}
