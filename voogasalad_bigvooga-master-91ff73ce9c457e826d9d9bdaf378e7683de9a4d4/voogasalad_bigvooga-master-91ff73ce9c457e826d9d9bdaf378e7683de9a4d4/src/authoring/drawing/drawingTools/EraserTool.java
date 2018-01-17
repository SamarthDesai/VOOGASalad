package authoring.drawing.drawingTools;

import authoring.drawing.ImageCanvas;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Erases smooth curves on the canvas.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class EraserTool extends SmoothDrawer {
	
	private static final double MIN_MAGNITUDE_RATIO = .3;
	private Rectangle square;

	public EraserTool(String name, ImageCanvas canvas) {
		super(name, canvas);
	}
	
	private void handleSquare(Point2D newCenter) {
		canvas.getChildren().remove(square);
		double stroke = canvas.getStroke();
		square = new Rectangle(newCenter.getX()-stroke/2, newCenter.getY()-stroke/2, stroke, stroke);
		square.setFill(Color.WHITE);
		square.setStroke(Color.BLACK);
		canvas.getChildren().add(square);
		canvas.eraseLine(newCenter, newCenter);
	}

	@Override
	protected void draw(Point2D lastLoc, Point2D point) {
		if(lastLoc.subtract(point).magnitude()>MIN_MAGNITUDE_RATIO*canvas.getStroke())
			canvas.eraseLine(lastLoc, point);
		else
			handleSquare(point);
	}
	
	@Override
	public void use() {
		super.use();
		canvas.setOnMouseReleased(e-> canvas.getChildren().remove(square));
	}

	@Override
	public void drop() {
		super.drop();
		canvas.setOnMouseReleased(null);
	}
}
