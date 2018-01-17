package engine.utilities.collisions;

import java.util.Collection;

import javafx.geometry.Point2D;

/**
 * The highest level class representing a bounding geometry. This class
 * hierarchy uses the Visitor pattern to recover subclass type information.
 * Although subclasses need not be immutable, it is recommended.
 * 
 * It is assumed that the SAT applies to all BoundingGeometries -- that is, they
 * are closed and convex, and that all BoundingGeometries have points given
 * clockwise in the traditional programming grid (counter-clockwise in a
 * traditional right-handed mathematical system).
 * 
 * Depends on BoundingSet to compute collisions with this.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class BoundingGeometry {
	/**
	 * This method computes the projection of the closed shape onto
	 * 
	 * @param vectorDirection
	 *            The linear direction to project onto
	 * @return The minimum and maximum location of the projection of this object (as
	 *         a distance from the origin, rather than as an explicit vector).
	 */
	public abstract Range dotted(Point2D vectorDirection);

	/**
	 * @param g
	 *            The geometry to check this against
	 * @return A vector representing the distance and direction THIS geometry must
	 *         be moved to separate from g. Null if no collision is occurring.
	 */
	public abstract Point2D checkCollision(BoundingGeometry g);

	/**
	 * For the visitor design pattern to recover subclass types.
	 * 
	 * @see #checkCollision
	 */
	public abstract Point2D checkPolygonCollision(BoundingPolygon polygon);

	/**
	 * For the visitor design pattern to recover subclass types.
	 * 
	 * @see #checkCollision
	 */
	public abstract Point2D checkPointCollision(BoundingPoint point);

	/**
	 * Has no side-effects on this object.
	 * 
	 * @param xFactor
	 *            The factor in x by which to scale this about the origin.
	 * @param yFactor
	 *            The factor in y by which to scale this about the origin.
	 * @return A new BoundingGeometry that has been scaled the given factors about
	 *         the origin.
	 */
	public abstract BoundingGeometry getScaled(double xFactor, double yFactor);

	/**
	 * Has no side-effects on this object.
	 * 
	 * @param rotation
	 *            given in degrees clockwise about the origin
	 * @return A new BoundingGeometry that has been rotated by rotation about the
	 *         origin.
	 */
	public abstract BoundingGeometry getRotated(double rotation);

	/**
	 * @param dx
	 *            shift in x
	 * @param dy
	 *            shift in y
	 * @return A new BoundingGeometry that has been translated by dx, dy.
	 */
	public abstract BoundingGeometry getTranslated(double dx, double dy);

	/**
	 * @param s
	 *            A BoundingSet to check collisions against
	 * @return Negative one times BoundingSet's checkCollision
	 * @see BoundingSet#checkCollision(BoundingGeometry)
	 */
	public Point2D checkCollision(BoundingSet s) {
		return negativeOf(s.checkCollision(this));
	}

	protected Point2D checkCollisions(Collection<Point2D> normalVectors, BoundingGeometry other) {
		double minOverlap = Integer.MAX_VALUE;
		Point2D direction = null;

		for (Point2D normal : normalVectors) {
			double overlap = other.dotted(normal).getOverlap(dotted(normal));
			if (overlap == 0)
				return null;
			if (Math.abs(overlap) < Math.abs(minOverlap)) {
				minOverlap = overlap;
				direction = normal;
			}
		}
		return direction.multiply(minOverlap);
	}

	protected Point2D negativeOf(Point2D vector) {
		if (vector == null)
			return null;
		return vector.multiply(-1);
	}
}
