package engine;

import java.util.List;

import controller.player.PlayerManager;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

/**
 * This interface is a concession to the amount of information required by the 
 * actions and operations. It contains all methods that are required by these. 
 * They are not documented in detail, because they merely give access to identically 
 * named methods in Layer, GameMaster.
 * 
 * @author Ian Eldridge-Allegra
 */
public interface GameObjectEnvironment {

	public PlayerManager getPlayerManager();
	public GameObject getWithName(String name);
	public void removeGameObject(GameObject withName);
	public void setNextWorld(String evaluate);
	public List<GameObject> getObjectsWithTag(String evaluate);
	public void addGameObject(GameObject obj);
	public GameObject getGameObject(String name);
	public void addElement(Element e);
	public void transfer(GameObject gameObject, String newWorld, String string);
	public void save(DoubleOperation currentPoints);
	public Point2D getAbsoluteMouseCoordinates();
	public void removeElement(Element element);
	public void exitToMenu();
	public GameObject getByID(String uniqueID);
	public boolean inBounds(GameObject obj);
	public GlobalVariables getGlobals();
}
