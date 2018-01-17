package engine.utilities.collisions;

import javafx.geometry.Point2D;

/**
 * A Point representing "Bounds," but without any size
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundingPoint extends BoundingGeometry {

	private Point2D point;

	public BoundingPoint(Point2D point) {
		this.point = point;
	}
	
	public BoundingPoint(double x, double y) {
		this(new Point2D(x, y));
	}
	
	public Point2D getPoint() {
		return point;
	}
	
	@Override
	public Range dotted(Point2D vectorDirection) {
		double dot = point.dotProduct(vectorDirection);
		return new Range(dot, dot);
	}

	@Override
	public Point2D checkCollision(BoundingGeometry g) {
		return negativeOf(g.checkPointCollision(this));
	}

	@Override
	public Point2D checkPolygonCollision(BoundingPolygon polygon) {
		return checkCollisions(polygon.generateOutwardNormals(), polygon);
	}

	@Override
	public Point2D checkPointCollision(BoundingPoint point) {
		return equals(point)?new Point2D(0,0):null;
	}

	@Override
	public BoundingGeometry getScaled(double xFactor, double yFactor) {
		return new BoundingPoint(new Point2D(point.getX() * xFactor, point.getY() * yFactor));
	}

	@Override
	public BoundingGeometry getRotated(double rotation) {
		BoundingPolygon.rotateByAngle(point, rotation);
		return new BoundingPoint(point);
	}

	@Override
	public BoundingGeometry getTranslated(double dx, double dy) {
		return new BoundingPoint(point.getX()+dx, point.getY()+dy);
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof BoundingPoint && ((BoundingPoint)o).getPoint().equals(getPoint());
	}
}
