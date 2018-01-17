package engine;

import java.util.ArrayList;
import java.util.List;

import engine.operations.booleanops.BooleanOperation;
import engine.operations.booleanops.ScreenClicked;
import engine.sprite.BoundedImage;
import engine.sprite.Displayable;
import engine.sprite.DisplayableImage;
import engine.sprite.DisplayablePane;
import engine.utilities.collisions.BoundingPoint;

/**
 * 
 * @author Nikolas Bramblett, Aaron Paskin
 *
 */
public class Inventory implements Element {

	private List<Holdable> objects;
	private BoundedImage pane;
	private GameObject holder;
	private int rowSpan, colSpan;
	private double x, y;
	private int startIndex;
	private String name;

	private Holdable selected;

	public static final int DEFAULT_ROWSPAN = 5;
	public static final int DEFAULT_COLSPAN = 5;

	public Inventory(GameObject holder, double x, double y) {
		this(holder, holder.getName() + "Inventory", x, y, DEFAULT_ROWSPAN, DEFAULT_COLSPAN, 0);
	}

	public Inventory(GameObject holder, String name, double x, double y, int rowSpan, int colSpan, int startIndex) {
		objects = new ArrayList<Holdable>();
		this.name = name;
		this.holder = holder;
		this.x = x;
		this.y = y;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.startIndex = startIndex;
	}

	public void setPane(BoundedImage pane) {
		this.pane = pane;
	}

	public void addObject(Holdable newObject) {
		objects.add(newObject);
	}

	public void removeObject(Holdable objectToRemove) {
		objects.remove(objects.indexOf(objectToRemove));
	}

	public List<Holdable> getFullInventory() {
		return new ArrayList<Holdable>(objects);

	}

	@Override
	public Displayable getDisplayable() {
		pane.setPosition(x, y);
		List<List<DisplayableImage>> ret = new ArrayList<>();
		int i = startIndex;
		for (int r = 0; r < rowSpan; r++) {
			List<DisplayableImage> row = new ArrayList<>();
			for (int c = 0; c < colSpan; c++) {
				if (i >= objects.size()) {
					break;
				}
				row.add(objects.get(i).getDisplayable());
				i++;
			}
			ret.add(row);
		}
		pane.setPosition(x, y);
		return new DisplayablePane(pane, ret, rowSpan, colSpan);
	}

	@Override
	public void step(GameObjectEnvironment w) {
		BooleanOperation screenClicked = new ScreenClicked();
		if (screenClicked.evaluate(null, w)
				&& pane.checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(),
						w.getPlayerManager().getMouseXY().getY())) != null) {
			int i = 0;
			for (Holdable h : objects) {
				if (h.getDisplayable().checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(),
						w.getPlayerManager().getMouseXY().getY())) != null) {
					selected = h;
					h.select(holder, w);
				}
				i++;
			}
		}
	}

	private int getHoldableClicked(double mouseX, double mouseY) {
		double inventoryMouseX = mouseX - (x - 0.5 * pane.getWidth());
		double inventoryMouseY = mouseY - (y - 0.5 * pane.getHeight());
		if (inventoryMouseX < 0 || inventoryMouseX > pane.getWidth() || inventoryMouseY < 0
				|| inventoryMouseY > pane.getHeight()) {
			return -1;
		}
		int col = (int) Math.round(pane.getWidth() / inventoryMouseX);
		int row = (int) Math.round(pane.getHeight() / inventoryMouseY);
		return row * colSpan + col;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public Holdable getSelected() {
		return selected;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public Inventory clone() {

		Inventory copy = new Inventory(holder, name, x, y, rowSpan, colSpan, startIndex);
		for (Holdable h : objects) {
			copy.addObject(h.clone());
		}

		return copy;

	}

}
