package engine.sprite;

/**
 * A displayable that can be placed relative to another Positionable (ie one
 * with a relative position).
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public interface RelativeDisplayable extends Displayable {

	public void setRelativePosition(Positionable p);

	public Positionable getRelativePosition();

}
