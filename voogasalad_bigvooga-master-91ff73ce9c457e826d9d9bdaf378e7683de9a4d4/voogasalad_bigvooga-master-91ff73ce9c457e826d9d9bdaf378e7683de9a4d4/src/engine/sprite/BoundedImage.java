package engine.sprite;

import java.util.ArrayList;
import java.util.List;

import engine.utilities.collisions.BoundingGeometry;
import engine.utilities.collisions.BoundingSet;
import engine.utilities.collisions.RelativeBoundingPolygon;
import gui.player.GameDisplay;

/**
 * Represents an Image with relative bounds. 
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public class BoundedImage extends BoundingSet implements DisplayableImage {

	private final static int DEFAULT_DEPTH = 10;
	
	private List<RelativeBoundingPolygon> relativeBounds;
	private String fileName;

	private Positionable relativePosition;

	public BoundedImage(String fileName) {
		super(DEFAULT_DEPTH);
		this.fileName = fileName;
		relativeBounds = new ArrayList<>();
		relativeBounds.add(RelativeBoundingPolygon.DEFAULT);
	}

	public BoundedImage(String fileName, List<RelativeBoundingPolygon> bounds) {
		this(DEFAULT_DEPTH, fileName, bounds);
	}
	
	public BoundedImage(int drawingPriority, String fileName, List<RelativeBoundingPolygon> bounds) {
		super(drawingPriority);
		this.fileName = fileName;
		relativeBounds = bounds;
	}
	
	public List<RelativeBoundingPolygon> getRelativeGeometries() {
		return new ArrayList<>(relativeBounds);
	}
	
	protected List<BoundingGeometry> getGeometry() {
		List<BoundingGeometry> geometry = new ArrayList<>(relativeBounds.size());
		for (RelativeBoundingPolygon rg : relativeBounds) {
			geometry.add(rg.getBoundingGeometry(this));
		}
		return geometry;
	}

	public String getFileName() {
		return fileName;
	}
	
	public BoundedImage clone() {
		BoundedImage i =  new BoundedImage(getDrawingPriority(), fileName, relativeBounds);
		i.setPosition(getX(), getY());
		i.setHeading(getHeading());
		i.setSize(getWidth(), getHeight());
		return i;
	}

	@Override
	public void visit(GameDisplay display) {
		display.displayImage(this);
	}

	@Override
	public Positionable getRelativePosition() {
		return relativePosition;
	}
	
	@Override
	public void setRelativePosition(Positionable pos) {
		relativePosition = pos;
	}
}
