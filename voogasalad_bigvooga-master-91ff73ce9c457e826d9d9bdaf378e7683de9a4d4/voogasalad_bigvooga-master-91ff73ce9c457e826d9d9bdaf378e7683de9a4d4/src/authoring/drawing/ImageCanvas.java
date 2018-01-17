package authoring.drawing;

import java.util.function.Consumer;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;

/**
 * The main canvas that is drawn on by a user. It is primarily edited by drawing tools
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ImageCanvas extends Pane {
	private DrawingTool currentTool;
	private int stroke;
	private Canvas canvas;

	public ImageCanvas(double width, double height) {
		canvas = new Canvas(width, height);
		getChildren().add(canvas);
	}

	/**
	 * @param tool
	 *            theNewToolToUse
	 */
	public void setTool(DrawingTool tool) {
		if (currentTool != null)
			currentTool.drop();
		currentTool = tool;
		if (tool != null)
			tool.use();
	}

	/**
	 * From
	 * https://stackoverflow.com/questions/18260421/how-to-draw-image-rotated-on-javafx-canvas
	 * 
	 * Sets the transform for the GraphicsContext to rotate around a pivot point.
	 *
	 * @param gc
	 *            the graphics context the transform to applied to.
	 * @param angle
	 *            the angle of rotation.
	 * @param px
	 *            the x pivot coordinate for the rotation (in canvas coordinates).
	 * @param py
	 *            the y pivot coordinate for the rotation (in canvas coordinates).
	 */
	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	/**
	 * Draws a line with rounded ends using the current stroke width and color.
	 * 
	 * @param lastLoc
	 *            The first point of the line
	 * @param point
	 *            The last point of the line
	 */
	public void drawLine(Point2D lastLoc, Point2D point) {
		handleLine(lastLoc, point, diff->canvas.getGraphicsContext2D().fillRoundRect(lastLoc.getX() - stroke / 2, lastLoc.getY() - stroke / 2, diff + stroke, stroke,
				stroke, stroke));
	}

	/**
	 * Erases a line along the path using a square with the current stroke width.
	 * 
	 * @param lastLoc
	 *            The first point of the line
	 * @param point
	 *            The last point of the line
	 */
	public void eraseLine(Point2D lastLoc, Point2D point) {
		handleLine(lastLoc, point, diff -> canvas.getGraphicsContext2D().clearRect(lastLoc.getX() - stroke / 2,
				lastLoc.getY() - stroke / 2, diff + stroke, stroke));
	}

	private void handleLine(Point2D lastLoc, Point2D point, Consumer<Double> diffConsumer) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.save();
		Point2D diff = point.subtract(lastLoc);
		rotate(gc, Math.toDegrees(Math.atan2(diff.getY(), diff.getX())), lastLoc.getX(), lastLoc.getY());
		diffConsumer.accept(diff.magnitude());
		gc.restore();
	}

	public void setStroke(int strokeValue) {
		stroke = strokeValue;
	}

	public void setColor(Color c) {
		canvas.getGraphicsContext2D().setFill(c);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * A convenience method to produce a line between the given points with rounded
	 * ends and the current color/stroke width.
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public Line getLine(Point2D point1, Point2D point2) {
		Line l = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		l.setStrokeWidth(stroke);
		l.setStroke(canvas.getGraphicsContext2D().getFill());
		l.setStrokeLineCap(StrokeLineCap.ROUND);
		return l;
	}

	public double getStroke() {
		return stroke;
	}
}
