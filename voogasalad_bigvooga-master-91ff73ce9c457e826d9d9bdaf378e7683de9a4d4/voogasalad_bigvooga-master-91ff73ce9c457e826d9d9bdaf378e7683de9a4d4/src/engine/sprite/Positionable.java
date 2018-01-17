package engine.sprite;

/**
 * An object that can be moved/stretched and whose full position can be accessed. 
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public interface Positionable {

	public int getDrawingPriority();
	public void setPosition(double x, double y);
	public double getX();
	public double getY();
	public void setSize(double width, double height);
	public double getWidth();
	public double getHeight();
	public void setHeading(double heading);
	public double getHeading();

}