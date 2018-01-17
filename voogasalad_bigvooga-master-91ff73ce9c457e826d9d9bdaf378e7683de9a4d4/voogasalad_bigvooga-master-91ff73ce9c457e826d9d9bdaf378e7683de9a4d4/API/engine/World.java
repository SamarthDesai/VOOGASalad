package engine;
import java.util.List;

public interface World extends Iterable<GameObject>{
	public void addGameObject(GameObject obj);
	public void removeGameObject(GameObject obj);
	public List<GameObject> getWithTag(String tag);
}
