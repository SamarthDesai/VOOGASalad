package player;

/**
 * Represents the front-end version of a Game Object
 * 
 */
public interface ViewObject {

	/**
	 * @return id of the object
	 */
	public int getID();

	/**
	 * @return node representing the object
	 */
	public int getParent();
	
	public int getX();
	public int getY();
	public int getImageView();

}
