package engine.sprite;

import java.util.List;

import engine.utilities.collisions.RelativeBoundingPolygon;
import gui.player.GameDisplay;

/**
 * Not currently used -- supports drawing solid colors instead of images.
 * 
 * Notably, this is kind of hacky -- it uses the "filename" to store the color
 * used. This should have been refactored to a new name, and a hierarchy could
 * have been developed for these two classes.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundedSolidColor extends BoundedImage {

	public BoundedSolidColor(int drawingPriority, String color, List<RelativeBoundingPolygon> bounds) {
		super(drawingPriority, color, bounds);
	}

	@Override
	public void visit(GameDisplay disp) {
		disp.drawRectangle(this, getFileName());
	}

}
