package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import engine.sprite.BoundedImage;
import engine.sprite.CompositeImage;
import engine.sprite.Displayable;
import engine.sprite.DisplayableText;
import engine.sprite.Sprite;
import engine.utilities.collisions.CollisionEvent;
import javafx.geometry.Point2D;

/**
 * The core of the game. Everything visible will be a GameObject. GameObjects
 * have 2 paradigms: Variables are just named quantities unique to each instance
 * of an object, such as names or coordinates.
 * 
 * Step() calls the Object's conditions and actions, which evaluate and modify
 * its current state based on the conditions of the game.
 * 
 * @author Nikolas Bramblett, Ian Eldridge-Allegra, Aaron Paskin
 *
 */
public class GameObject extends TaggableSpriteableVariableContainer implements Element {

	private static final double DEFAULT_SIZE = 200;
	private static final String DEFAULT_NAME = "unnamed";
	private static final String DEFAULT_TAG = "default";

	public static final String CAMERA_TAG = "camera";

	private Map<Condition, List<Action>> events;

	private String uniqueID;
	private String name;

	private CollisionEvent lastCollision;
	private Inventory inventory;
	protected List<DisplayableText> dialogueHandler = new ArrayList<>();

	private double heading;
	private List<Point2D> ithDerivative;
	private Path pathToFollow;
	private double width = DEFAULT_SIZE;
	private double height = DEFAULT_SIZE;

	public GameObject() {
		this(DEFAULT_NAME);
	}

	public GameObject(String name) {
		tagSet = new HashSet<String>();
		doubleVars = new HashMap<String, Double>();
		booleanVars = new HashMap<String, Boolean>();
		stringVars = new HashMap<String, String>();
		events = new HashMap<>();
		this.name = name;
		tagSet.add(name);
		tagSet.add(DEFAULT_TAG);
		ithDerivative = new ArrayList<>();
		ithDerivative.add(new Point2D(0, 0));
		inventory = new Inventory(this, getX(), getY());
	}

	public String getName() {
		return name;
	}

	public void addConditionAction(Condition c, List<Action> a) {
		events.put(c, a);
	}

	/**
	 * Steps animation and checks/executes events in order of priority
	 */
	public void step(int priorityNum, GameObjectEnvironment w, List<Runnable> runnables) {
		for (Condition c : events.keySet()) {
			if (c.getPriority() == priorityNum && c.isTrue(this, w)) {
				for (Action a : events.get(c)) {
					runnables.add(() -> a.execute(this, w));
				}
			}
		}
	}

	@Override
	public void step(GameObjectEnvironment w) {
		if(sprite != null)
			sprite.step();
		for (int i = ithDerivative.size() - 1; i > 0; i--) {
			ithDerivative.set(i - 1, ithDerivative.get(i - 1).add(ithDerivative.get(i)));
		}
	}

	/**
	 * Setter for x and y Coordinates
	 * 
	 * @param x,
	 *            y
	 */
	public void setLocation(double x, double y) {
		setLocation(new Point2D(x, y));
	}

	public void setLocation(Point2D loc) {
		setDerivative(0, loc);
	}

	public Point2D getLocation() {
		return getDerivative(0);
	}

	public Point2D getDerivative(int i) {
		if (i < ithDerivative.size())
			return ithDerivative.get(i);
		return new Point2D(0, 0);
	}

	public void setDerivative(int i, Point2D vector) {
		while (ithDerivative.size() <= i) {
			ithDerivative.add(new Point2D(0, 0));
		}
		ithDerivative.set(i, vector);
	}

	public void stop() {
		while (ithDerivative.size() > 1)
			ithDerivative.remove(ithDerivative.size() - 1);
	}

	public double getX() {
		return getLocation().getX();
	}

	public double getY() {
		return getLocation().getY();
	}

	public void setHeading(double newHeading) {
		heading = newHeading;
	}

	public double getHeading() {
		return heading;
	}

	/**
	 * Compiles all priorities of Conditions into an iterable set. Used by Layer to
	 * call Events in proper order.
	 * 
	 * @return {Set<Integer>} priorities
	 */
	public Set<Integer> getPriorities() {
		Set<Integer> priorities = new TreeSet<Integer>();
		for (Condition c : events.keySet()) {
			priorities.add(c.getPriority());
		}
		return priorities;
	}

	/**
	 * Returns the current BoundedImage of this Object.
	 * 
	 * @return BoundedImage
	 */
	public BoundedImage getBounds() {
		BoundedImage result = sprite.getImage();
		result.setPosition(getX(), getY());
		result.setHeading(heading);
		result.setSize(width, height);
		return result;
	}

	@Override
	public Displayable getDisplayable() {
		return new CompositeImage(getBounds(), dialogueHandler);
	}

	/**
	 * Creates a new instance of this game object, which has the same values (but
	 * can take new values)
	 */
	public GameObject clone() {
		GameObject copy = new GameObject(name);
		copy.setLocation(getLocation());
		copy.setHeading(heading);
		copy.setSize(width, height);
		copy.setUniqueID(uniqueID);
		cloneHelp(copy);
		for (Condition c : events.keySet())
			copy.addConditionAction(c, new ArrayList<>(events.get(c)));
		copy.setDialogue(dialogueHandler);
		copy.inventory = inventory.clone();
		return copy;
	}

	public CollisionEvent getLastCollisionChecked() {
		return lastCollision;
	}

	public void setLastCollisionChecked(CollisionEvent collisionEvent) {
		lastCollision = collisionEvent;
	}

	/**
	 * @return the uniqueID
	 */
	public String getUniqueID() {
		return uniqueID;
	}

	/**
	 * @param uniqueID
	 *            the uniqueID to set
	 */
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public Point2D getDimensions() {
		return new Point2D(width, height);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setDialogue(List<DisplayableText> text) {
		dialogueHandler = text;
	}

	public void setDialogue(String s) {
		if (dialogueHandler.isEmpty())
			dialogueHandler.add(DisplayableText.DEFAULT);
		DisplayableText newText = dialogueHandler.get(0).getWithMessage(s);
		dialogueHandler = new ArrayList<DisplayableText>();
		dialogueHandler.add(newText);
	}
	
	public void addToPath(Point2D point)
	{
		pathToFollow.addPathPoint(point);
	}
	public void moveOnPath(double speed)
	{
		pathToFollow.moveToTarget(this, speed);
	}
}
