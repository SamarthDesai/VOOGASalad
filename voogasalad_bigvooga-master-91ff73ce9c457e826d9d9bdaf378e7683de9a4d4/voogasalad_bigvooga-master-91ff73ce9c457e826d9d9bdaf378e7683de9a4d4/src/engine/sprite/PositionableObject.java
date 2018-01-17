package engine.sprite;

/**
 * @see Positionable.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class PositionableObject implements Positionable {

	private int drawingPriority;
	private double x;
	private double y;
	private double width;
	private double height;
	private double heading;

	public PositionableObject() {
		this(0);
	}
	
	public PositionableObject(int drawingPriority) {
		this.drawingPriority = drawingPriority;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public double getHeading() {
		return heading;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}
	
	public int getDrawingPriority() {
		return drawingPriority;
	}

}