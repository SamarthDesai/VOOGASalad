package engine;

import java.util.List;

import controller.player.PlayerManager;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

/**
 * This class provides a wrapper for the master, world, and layer to shield some
 * of their methods from Actions/Operations, while also allowing for access to 
 * some methods that are needed.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ConcreteGameObjectEnvironment implements GameObjectEnvironment {

	private GameMaster master;
	private GameWorld world;
	private Layer layer;

	public void setGameMaster(GameMaster master) {
		this.master = master;
	}

	public void setGameWorld(GameWorld world) {
		this.world = world;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	@Override
	public PlayerManager getPlayerManager() {
		return master.getPlayerManager();
	}

	@Override
	public void addGameObject(GameObject obj) {
		layer.addGameObject(obj);
	}

	@Override
	public GameObject getWithName(String name) {
		return layer.getWithName(name);
	}

	@Override
	public void removeGameObject(GameObject withName) {
		layer.removeGameObject(withName);
	}

	@Override
	public void setNextWorld(String nextWorld) {
		master.setNextWorld(nextWorld);
	}

	@Override
	public List<GameObject> getObjectsWithTag(String tag) {
		return layer.getObjectsWithTag(tag);
	}

	@Override
	public void transfer(GameObject gameObject, String newWorld, String layerName) {
		GameWorld world = master.getWorldWithName(newWorld);
		for (Layer l : world.getLayers()) {
			if (l.isNamed(layerName)) {
				layer.removeGameObject(gameObject);
				l.addGameObject(gameObject);
			}
		}
	}

	@Override
	public void addElement(Element e) {
		layer.addElement(e);
	}

	@Override
	public GameObject getGameObject(String name) {
		return master.getBlueprints().getInstanceOf(name);
	}

	@Override
	public void save(DoubleOperation currentPoints) {
		master.save();
	}

	@Override
	public Point2D getAbsoluteMouseCoordinates() {
		return world.makeScreenCoordinatesAbsolute(master.getPlayerManager().getMouseXY().getX(),
				master.getPlayerManager().getMouseXY().getY());
	}

	@Override
	public void removeElement(Element element) {
		layer.removeElement(element);
	}

	@Override
	public void exitToMenu() {
		master.getPlayerManager().exitToMenu();
	}

	@Override
	public GameObject getByID(String uniqueID) {
		return layer.getByID(uniqueID);
	}

	@Override
	public boolean inBounds(GameObject obj) {
		return world.inBounds(obj);
	}

	public GlobalVariables getGlobals() {
		return master.getGlobals();
	}

}
