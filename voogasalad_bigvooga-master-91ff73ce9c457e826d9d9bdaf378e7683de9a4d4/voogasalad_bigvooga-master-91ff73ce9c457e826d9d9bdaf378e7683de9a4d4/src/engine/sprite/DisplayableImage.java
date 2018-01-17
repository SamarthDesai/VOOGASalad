package engine.sprite;

public interface DisplayableImage extends RelativeDisplayable, Positionable {
	public String getFileName();
	public Positionable getRelativePosition();
}
