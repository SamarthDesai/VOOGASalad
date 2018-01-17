package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import engine.sprite.Displayable;
import engine.utilites.camera.Camera;
import javafx.geometry.Point2D;

/**
 * Holds Layers, which hold GameObjects. An Example of a GameWorld would be a
 * tavern room or a dark forest.
 * 
 * @author Nikolas Bramblett, ...
 *
 */
public class GameWorld {

	private final static String DEFAULT_NAME = "layer";

	private String worldName;
	private List<GameLayer> worldLayers;
	private Camera camera;

	public GameWorld() {
		this(DEFAULT_NAME);
	}

	public GameWorld(String name) {
		worldName = name;
		worldLayers = new ArrayList<>();
		camera = new Camera(null);
	}

	public boolean isNamed(String name) {
		return worldName.equals(name);
	}

	/**
	 * Calls step() on each layer
	 * 
	 * @param environment
	 */
	public void step(ConcreteGameObjectEnvironment environment) {
		environment.setGameWorld(this);
		for (Layer l : worldLayers)
			l.step(environment);
	}


	public List<GameObject> getAllGameObjects() {
		List<GameObject> obs = new ArrayList<>();
		for (Layer l : worldLayers) {

			obs.addAll(l.getAllGameObjects());
		}
		return obs;
	}

	// Not super proud of this implementation but it works.
	private Displayable drawWithParallax(Displayable d) {
		// TODO Auto-generated method stub
		Displayable temp = d;
		Point2D relCoords = camera.makeCoordinatesParallax(temp.getX(), temp.getY());
		temp.setPosition(relCoords.getX(), relCoords.getY());
		return temp;
	}

	/**
	 * Returns a list of all Displayables in the world, setting each one's location
	 * relative to the tracked object
	 */
	public List<Displayable> getAllDisplayables() {
		List<Displayable> ret = new ArrayList<>();
		GameObject player = getPlayerObject();
		camera = new Camera(player);
		camera.moveToPlayer();
		// Since layers are added in reverse order of depth, each layer is added and
		// sorted before the next one is included.
		for (GameLayer l : worldLayers) {
			if (l.isNamed("Background")) {
				if (l.getAllElements().size() == 1) {
					Displayable background = l.getAllElements().get(0).getDisplayable();
					ret.add(drawWithParallax(background));
				}
				continue;
			}
			List<Displayable> tempList = new ArrayList<Displayable>();
			for (Element e : l.getAllElements()) {
				Displayable image = e.getDisplayable();
				if (l.isTracked()) {
					Point2D relCoords = camera.makeCoordinatesRelative(e.getX(), e.getY());
					image.setPosition(relCoords.getX(), relCoords.getY());
				} else
					image.setPosition(e.getX(), e.getY());
				tempList.add(image);
			}
			Collections.sort(tempList, (i1, i2) -> i1.getDrawingPriority() - i2.getDrawingPriority());
			ret.addAll(tempList);
		}
		return ret;
	}

	private GameObject getPlayerObject() {
		for (GameLayer l : worldLayers) {
			List<GameObject> player = l.getObjectsWithTag(GameObject.CAMERA_TAG);
			if (player.size() > 0)
				return player.get(0);
		}
		return null;
	}

	public void addLayer(GameLayer layer) {
		worldLayers.add(layer);
	}

	public void removeLayer(String layerName) {
		for (Layer l : worldLayers) {
			if (l.isNamed(layerName)) {
				worldLayers.remove(l);
				return;
			}
		}
		// Placeholder for error I guess?
		;
	}

	public List<GameLayer> getLayers() {
		return worldLayers;
	}

	public Point2D makeScreenCoordinatesAbsolute(double x, double y) {
		return camera.makeCoordinatesAbsolute(x, y);
	}

	public boolean inBounds(GameObject obj) {
		return camera.inBounds(obj);
	}

	public String getName() {
		return worldName;
	}

}
