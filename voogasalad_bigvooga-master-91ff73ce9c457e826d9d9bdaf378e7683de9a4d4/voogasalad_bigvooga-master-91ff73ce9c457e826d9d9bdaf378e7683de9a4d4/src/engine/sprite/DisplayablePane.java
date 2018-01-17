package engine.sprite;

import java.util.List;

import gui.player.GameDisplay;

public class DisplayablePane implements Displayable, Positionable {

	private DisplayableImage paneImage;
	private List<List<DisplayableImage>> holdableImages;
	private int rowSpan, colSpan;
	
	public DisplayablePane(DisplayableImage paneImage, List<List<DisplayableImage>> holdableImages, int colSpan, int rowSpan) {
		this.paneImage = paneImage;
		this.holdableImages = holdableImages;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
	}

	@Override
	public void visit(GameDisplay display) {
		display.displayImage(paneImage);
		Double inventoryX = paneImage.getX();
		Double inventoryY = paneImage.getY();
		Double inventoryWidth  = paneImage.getWidth();
		Double inventoryHeight = paneImage.getHeight();
		Double x0 = inventoryX - 0.5*inventoryWidth;
		Double y0 = inventoryY - 0.5*inventoryHeight;
		Double cellHeight = inventoryHeight / rowSpan;
		Double cellWidth = inventoryWidth / colSpan;
		for(int r = 0; r < holdableImages.size(); r++) {
			for(int c = 0; c < holdableImages.get(r).size(); c++) {
				DisplayableImage h = holdableImages.get(r).get(c);
				h.setPosition(x0 + (cellWidth * c) + (0.5 * cellWidth), y0 + (cellHeight * r) + (0.5 * cellHeight));
				h.setSize(cellWidth, cellHeight);
				display.displayImage(h);
			}
		}
	}

	@Override
	public int getDrawingPriority() {
		return Integer.MAX_VALUE;
	}

	public void setPosition(double x, double y) {
		paneImage.setPosition(x, y);
	}

	public void setSize(double width, double height) {
		paneImage.setSize(width, height);
	}

	public void setHeading(double heading) {
		paneImage.setHeading(heading);
	}

	public double getX() {
		return paneImage.getX();
	}

	public double getY() {
		return paneImage.getY();
	}

	public double getWidth() {
		return paneImage.getWidth();
	}

	public double getHeight() {
		return paneImage.getHeight();
	}

	public double getHeading() {
		return paneImage.getHeading();
	}
	
}
