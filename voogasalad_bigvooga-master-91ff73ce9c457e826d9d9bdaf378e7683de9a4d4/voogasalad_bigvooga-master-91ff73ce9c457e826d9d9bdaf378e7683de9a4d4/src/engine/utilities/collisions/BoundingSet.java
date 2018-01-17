package engine.utilities.collisions;

import java.util.Collection;
import java.util.HashSet;

import engine.sprite.PositionableObject;
import javafx.geometry.Point2D;

/**
 * Represents multiple BoundingGeometries -- collisions can be checked against
 * other bounding sets or bounding geometries.
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public abstract class BoundingSet extends PositionableObject {
	
	public BoundingSet(int drawingPriority) {
		super(drawingPriority);
	}

	protected abstract Collection<BoundingGeometry> getGeometry();

	/**
	 * @param other
	 *            The other BoundingSet to check collisions against.
	 * @return The maximum overlap resolution vector for all geometry collisions
	 *         between the sets.
	 */
	public Point2D checkCollision(BoundingSet other) {
		Point2D result = null;
		double maxMagnitude = 0;
		for (BoundingGeometry thisGeometry : getGeometry()) {
			for (BoundingGeometry otherGeometry : other.getGeometry()) {
				Point2D collision = thisGeometry.checkCollision(otherGeometry);
				if (collision == null)
					continue;
				double magnitude = collision.magnitude();
				if (magnitude > maxMagnitude) {
					maxMagnitude = magnitude;
					result = collision;
				}
			}
		}
		return result;
	}

	/**
	 * @see #checkCollision(BoundingSet)
	 */
	public Point2D checkCollision(BoundingGeometry other) {
		return checkCollision(new SingleSet(other));
	}

	private class SingleSet extends BoundingSet {
		private HashSet<BoundingGeometry> set;

		public SingleSet(BoundingGeometry element) {
			super(0);
			set = new HashSet<BoundingGeometry>();
			set.add(element);
		}

		@Override
		protected Collection<BoundingGeometry> getGeometry() {
			return set;
		}
	}
}
