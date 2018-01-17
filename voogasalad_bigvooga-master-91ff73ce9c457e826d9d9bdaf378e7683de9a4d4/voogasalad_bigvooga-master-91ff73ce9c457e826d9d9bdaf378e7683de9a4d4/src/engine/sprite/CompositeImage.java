package engine.sprite;

import java.util.Collections;
import java.util.List;

import engine.VoogaException;
import engine.utilities.collisions.BoundingPolygon;
import gui.player.GameDisplay;
import javafx.geometry.Point2D;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class CompositeImage implements Displayable {

	private Displayable displayable1;
	private List<? extends RelativeDisplayable> others;

	public CompositeImage(Displayable bottom, List<? extends RelativeDisplayable> others) {
		displayable1 = bottom;
		this.others = others;
		if (displayable1 == null)
			throw new VoogaException("CompositeFail");
	}

	@Override
	public void visit(GameDisplay display) {
		displayable1.visit(display);
		for (RelativeDisplayable d : others) {
			Positionable t = d.getRelativePosition();
			d.setHeading(displayable1.getHeading());
			Point2D newLocation = BoundingPolygon
					.rotateByAngle(new Point2D(t.getX() * displayable1.getWidth(),
							t.getY() * displayable1.getHeight()), d.getHeading())
					.add(new Point2D(displayable1.getX(), displayable1.getY()));
			d.setPosition(newLocation.getX(), newLocation.getY());
			d.setSize(t.getWidth() * displayable1.getWidth(), t.getWidth() * displayable1.getHeight());
			d.visit(display);
		}
	}

	public int getDrawingPriority() {
		return displayable1.getDrawingPriority();
	}

	@Override
	public void setPosition(double x, double y) {
		displayable1.setPosition(x, y);
	}

	@Override
	public double getX() {
		return displayable1.getX();
	}

	@Override
	public double getY() {
		return displayable1.getY();
	}

	@Override
	public void setSize(double width, double height) {
		displayable1.setSize(width, height);
	}

	@Override
	public double getWidth() {
		return displayable1.getWidth();
	}

	@Override
	public double getHeight() {
		return displayable1.getHeight();
	}

	@Override
	public void setHeading(double heading) {
		displayable1.setHeading(heading);
	}

	@Override
	public double getHeading() {
		return displayable1.getHeading();
	}

}
