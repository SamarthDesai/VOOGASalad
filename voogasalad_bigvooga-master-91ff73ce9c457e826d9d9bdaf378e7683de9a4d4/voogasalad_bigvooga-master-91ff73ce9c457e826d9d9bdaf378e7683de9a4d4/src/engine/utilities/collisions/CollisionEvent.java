package engine.utilities.collisions;

import engine.GameObject;
import javafx.geometry.Point2D;

/**
 * Represents a collision event for a particular object
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public class CollisionEvent {

	private GameObject other;
	private Point2D overlap;

	public CollisionEvent(GameObject other, Point2D overlap) {
		this.other = other;
		this.overlap = overlap;
	}

	/**
	 * @return The other game object involved in the collision
	 */
	public GameObject getGameObject() {
		return other;
	}

	/**
	 * @return The minimum overlap vector, given directed towards the GameObject
	 *         this is associated with and away from the 'other' GameObject
	 */
	public Point2D getOverlapVector() {
		return overlap;
	}
}
