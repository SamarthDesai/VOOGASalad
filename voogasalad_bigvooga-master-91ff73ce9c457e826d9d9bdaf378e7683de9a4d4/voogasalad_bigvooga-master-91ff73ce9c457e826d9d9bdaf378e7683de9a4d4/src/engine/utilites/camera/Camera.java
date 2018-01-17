package engine.utilites.camera;

import engine.GameObject;
import javafx.geometry.Point2D;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class Camera {

	private GameObject playerObject;
	private final double SCREEN_WIDTH = 1000;
	private final double SCREEN_HEIGHT = 700;
	
	private final double PARALLAX = .1;

	// private double tolerance = 100;

	private double x = 0;
	private double y = 0;

	public Camera(GameObject player) {
		playerObject = player;
	}

	public void moveToPlayer() {
		if (playerObject != null) {
			this.x = playerObject.getX() - SCREEN_WIDTH / 2;
			this.y = playerObject.getY() - SCREEN_HEIGHT / 2;
		}
	}

	public Point2D makeCoordinatesRelative(double x, double y) {
		return new Point2D(x - this.x, y - this.y);
	}

	/**
	 * Used for coordinates of something like a mouse click, where it is relative to
	 * the screen, and thus the camera.
	 * 
	 * @param x
	 * @param y
	 * @return absolute coordinates
	 */
	public Point2D makeCoordinatesAbsolute(double x, double y) {
		return new Point2D(x + this.x, y + this.y);
	}
	
	public Point2D makeCoordinatesParallax(double x, double y)
	{
		Point2D temp = makeCoordinatesRelative(x, y);
		double deltaX = temp.getX()-x;
		double deltaY = temp.getY()-y;
		
		return new Point2D(x+deltaX*PARALLAX, y+deltaY*PARALLAX);
	}
	public boolean inBounds(GameObject obj)
	{
		Point2D relCoords = makeCoordinatesRelative(obj.getX(), obj.getY());
		return relCoords.getX()+obj.getDimensions().getX()/2 < 0 || relCoords.getX()-obj.getDimensions().getX()/2 > SCREEN_WIDTH
				|| relCoords.getY()+obj.getDimensions().getY()/2 < 0 || relCoords.getY()-obj.getDimensions().getY()/2 > SCREEN_HEIGHT;
		
	}

}
