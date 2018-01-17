package authoring.drawing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import engine.sprite.BoundedImage;
import engine.utilities.collisions.BoundingPolygon;
import engine.utilities.collisions.RelativeBoundingPolygon;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * This tool allows users to convert images into BoundedImages by drawing
 * polygon bounds on them. Because BoundedImages assume the geometries given to
 * them are convex and CCW in right-handed coordinates (CW in a comp-sci grid),
 * this utility forces the user to place points under that constraint and shows
 * a red phantom-line when the point would be illegal.
 * 
 * Left clicking places a point, and right clicking closes a shape. Notably,
 * this utility does not include any save-button/feature, so the public save
 * method has to be called in order for the new bounded image to be sent to the
 * consumer.
 * 
 * Relies on BoundingPolygon, RelativeBoundingGeometry, and BoundedImage.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundingPolygonCreator extends Pane {

	private static final double PHANTOM_OPACITY = .5;
	private static final double LINE_WIDTH = 3;
	private static final double WIDTH = 400;
	private List<RelativeBoundingPolygon> geometries = new ArrayList<>();
	private List<Point2D> vertices = new ArrayList<>();
	private Line phantomLine;
	private Consumer<BoundedImage> consumer;
	private String imageName;

	/**
	 * This is for convenience -- it merely uses the given function to convert the
	 * bounded image's name to an actual image, then applies
	 * {@link #BoundingPolygonCreator(Image, BoundedImage, Consumer)}.
	 * 
	 * @param imageLoader
	 * @param boundedImage
	 * @param consumer
	 */
	public BoundingPolygonCreator(Function<String, Image> imageLoader, BoundedImage boundedImage,
			Consumer<BoundedImage> consumer) {
		this(imageLoader.apply(boundedImage.getFileName()), boundedImage, consumer);
	}

	/**
	 * Loads in a previous BoundedImage and allows it to be added to then saved
	 * again.
	 * 
	 * @param image
	 *            The image to display (presumably the one represented by the
	 *            boundedImage.
	 * @param boundedImage
	 *            The BoundedImage whose bounds/image name should be loaded in.
	 * @param consumer
	 *            How to save the BoundedImage.
	 */
	public BoundingPolygonCreator(Image image, BoundedImage boundedImage, Consumer<BoundedImage> consumer) {
		this(image, boundedImage.getFileName(), consumer);
		for (RelativeBoundingPolygon geometry : boundedImage.getRelativeGeometries()) {
			geometries.add(geometry);
			List<Point2D> points = geometry
					.getBoundingGeometry(getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 0).getVertices();
			for (int i = 0; i < points.size() - 1; i++) {
				addLine(generateLine(points.get(i), points.get(i + 1)));
			}
			addLine(generateLine(points.get(0), points.get(points.size() - 1)));
		}
	}

	/**
	 * Allows an image to be converted to a BoundedImage with bounds given by the
	 * user.
	 * 
	 * @param image
	 *            The image to which bounds should be applied by the user.
	 * @param imageName
	 *            The short file name (eg 'Duvall.png') that will be stored into the
	 *            BoundedImage for later use.
	 * @param consumer
	 *            How to save the resulting BoundedImage.
	 */
	public BoundingPolygonCreator(Image image, String imageName, Consumer<BoundedImage> consumer) {
		ImageView view = new ImageView(image);
		double scale = WIDTH / image.getWidth();
		view.setFitWidth(image.getWidth() * scale);
		view.setFitHeight(image.getHeight() * scale);
		getChildren().add(view);

		this.imageName = imageName;
		this.consumer = consumer;
		setup();
	}

	/**
	 * Saves the current BoundedImage to the consumer. If no geometries have been
	 * closed, then a default rectangle is sent.
	 */
	public void save() {
		if (geometries.isEmpty())
			consumer.accept(new BoundedImage(imageName));
		else
			consumer.accept(new BoundedImage(imageName, geometries));
	}

	private void setup() {
		setOnMouseClicked(this::mouseClicked);
		setOnMouseMoved(this::mouseMoved);
	}

	private void mouseClicked(MouseEvent event) {
		if (event.getButton().equals(MouseButton.SECONDARY))
			rightClick(event);
		else
			leftClick(event);
	}

	private void leftClick(MouseEvent event) {
		Point2D clicked = new Point2D(event.getX(), event.getY());
		if (!isLegalNextPoint(clicked))
			return;
		if (!vertices.isEmpty()) {
			addLine(generateLine(lastPoint(), clicked));
		}
		vertices.add(clicked);
		changePhantomLine(new Line());
	}

	private boolean isLegalNextPoint(Point2D point) {
		if (vertices.size() < 2)
			return true;
		Point2D previousSide = lastPoint().subtract(vertices.get(vertices.size() - 2));
		Point2D nextSide = point.subtract(lastPoint());
		if (previousSide.crossProduct(nextSide).getZ() < 0)
			return false;
		Point2D finalSide = vertices.get(0).subtract(point);
		if (nextSide.crossProduct(finalSide).getZ() < 0)
			return false;
		Point2D originalSide = vertices.get(1).subtract(vertices.get(0));
		return finalSide.crossProduct(originalSide).getZ() >= 0;
	}

	private void addLine(Line l) {
		getChildren().add(l);
	}

	private Line generateLine(Point2D start, Point2D end) {
		Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
		line.setStrokeWidth(LINE_WIDTH);
		return line;
	}

	private void changePhantomLine(Line newLine) {
		if (phantomLine != null)
			getChildren().remove(phantomLine);
		phantomLine = newLine;
		if (phantomLine != null)
			getChildren().add(phantomLine);
	}

	private Point2D lastPoint() {
		return vertices.get(vertices.size() - 1);
	}

	private void rightClick(MouseEvent event) {
		if (vertices.isEmpty())
			return;
		addLine(generateLine(lastPoint(), vertices.get(0)));
		changePhantomLine(null);
		generateRelativeGeometry();
	}

	private void generateRelativeGeometry() {
		BoundingPolygon poly = new BoundingPolygon(vertices);
		poly = (BoundingPolygon) poly.getScaled(1.0 / getWidth(), 1.0 / getHeight()).getTranslated(-.5, -.5);
		geometries.add(new RelativeBoundingPolygon(poly));
		vertices.clear();
	}

	private void mouseMoved(MouseEvent event) {
		if (phantomLine == null)
			return;
		Point2D location = new Point2D(event.getX(), event.getY());
		Line newPhantom = generateLine(lastPoint(), location);
		newPhantom.setOpacity(PHANTOM_OPACITY);
		if (!isLegalNextPoint(location))
			newPhantom.setStroke(Color.RED);
		changePhantomLine(newPhantom);
	}
}
