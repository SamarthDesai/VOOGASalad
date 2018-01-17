package engine.utilities.collisions;

import java.util.ArrayList;
import java.util.List;

import engine.sprite.Positionable;
import javafx.geometry.Point2D;

/**
 * Uses composition to represent BoundingGeometries of relative size and
 * position that can be scaled/translated to produce a boundingGeometry
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public class RelativeBoundingPolygon {
	public static final RelativeBoundingPolygon DEFAULT = defaultRectangle();

	private static RelativeBoundingPolygon defaultRectangle() {
		List<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D(-.5, -.5));
		points.add(new Point2D(.5, -.5));
		points.add(new Point2D(.5, .5));
		points.add(new Point2D(-.5, .5));
		return new RelativeBoundingPolygon(new BoundingPolygon(points));
	}

	private BoundingPolygon geometry;

	/**
	 * @param geometry
	 *            Geometry given in relative coordinates from -0.5 to .5 in both
	 *            directions
	 */
	public RelativeBoundingPolygon(BoundingPolygon geometry) {
		this.geometry = geometry;
	}

	/**
	 * @param position gives the position, size, and heading
	 * @return The relative bounding geometry as it would be imposed on the positionable
	 */
	public BoundingPolygon getBoundingGeometry(Positionable position) {
		return getBoundingGeometry(position.getX(), position.getY(), position.getWidth(), position.getHeight(),
				position.getHeading());
	}

	/**
	 * @see #getBoundingGeometry(Positionable)
	 */
	public BoundingPolygon getBoundingGeometry(double x, double y, double width, double height, double heading) {
		return (BoundingPolygon) geometry.getScaled(width, height).getRotated(heading).getTranslated(x, y);
	}

	public String toString() {
		return geometry.toString();
	}

}
