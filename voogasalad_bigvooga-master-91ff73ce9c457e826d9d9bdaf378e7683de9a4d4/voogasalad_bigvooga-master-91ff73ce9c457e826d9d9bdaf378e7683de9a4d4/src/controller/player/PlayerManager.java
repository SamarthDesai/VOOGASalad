package controller.player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.EngineController;
import engine.sprite.Displayable;
import engine.utilities.data.GameDataHandler;
import gui.player.GameDisplay;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

/**
 * Acts as the intermediary class between the game engine and player by using the instances of both GameDisplay and EngineController (which
 * contains the GameMaster engine class) and communication-specific methods within them to pass any user input from the player, handle it
 * accordingly in the engine, and then pass the changed objects back to the player.
 * 
 * @author Samarth, Ian, and Aaron
 *
 */
public class PlayerManager {

	private GameDisplay gameDisplay;
	private EngineController engineController;
	private GameDataHandler gameDataHandler;
	
	private Set<String> keysDown = new HashSet<>();
	private Set<String> prevKeysDown = new HashSet<>();
	
	private boolean primaryButtonDown = false;
	private boolean prevPrimaryButtonDown = false;
	private double clickX;
	private double clickY;
	private double mouseX;
	private double mouseY;
	private String typed = "";
	private DataView dataView;
	
	/**
	 * Empty constructor for PlayerManager.
	 * @param gameDataHandler 
	 */
	public PlayerManager(GameDataHandler gameDataHandler) {
		this.gameDataHandler = gameDataHandler;
	}
	
	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}
	
	/**
	 * Sets the current set of keys pressed for the game engine, which allows the engine to detect if a key is being pressed
	 * to offer the appropriate action for a button press to be passed back to the player.
	 * 
	 * @return the set of keys being pressed
	 */
	public Set<String> getKeysDown() {
		return keysDown;
	}
	
	/**
	 * Sets the set of keys pressed from the previous step for the game engine, which allows the engine to detect if a key is being pressed
	 * down continuously, allowing it to offer the appropriate action for a continuous button press to be passed back to the player.
	 * 
	 * @return the previous set of keys that were pressed
	 */
	public Set<String> getPrevKeysDown() {
		return prevKeysDown;
	}
	
	/**
	 * Sets the key that is pressed in the player, then sets it here in PlayerManager to be sent to the engine to calculate what should occur
	 * in the player. This also allows for multiple keys to be pressed simultaneously since keysDown is a set of keys.
	 * 
	 * @param keyCode - The key that was pressed in the player
	 */
	public void setKeyPressed(KeyCode keyCode) {
		
		if(keyCode.equals(KeyCode.F1))
		{
			gameDisplay.debugMenu(engineController);
		}
		else
			keysDown.add(keyCode.getName());
	}
	
	/**
	 * Sets the key that is being released in the player, and removes it from the set keysDown to allow for the next set of keys being pressed
	 * to be added without incorrectly having the previous keys stored.
	 * 
	 * @param keyCode - The key that is being released in the player
	 */
	public void setKeyReleased(KeyCode keyCode) {
		keysDown.remove(keyCode.getName());
	}
	
	public void setCharTyped(String string) {
		if(!string.equals("\b"))
			typed += string;
		else if(typed.length() > 0)
			typed = typed.substring(0, typed.length()-1);
	}
	
	/**
	 * Communicates that the primary mouse button is being clicked through its boolean and sets the x and y values of where it was clicked in PlayerManager
	 * based on where the click occurred in the player. This information is then passed to engine for appropriate calculation based on what/where
	 * the click acts on.
	 * 
	 * @param x - X coordinate
	 * @param y - X coordinate
	 */
	public void setPrimaryButtonDown(double x, double y) {
		primaryButtonDown = true;
		clickX = x;
		clickY = y;
	}
	
	/**
	 * When the primary mouse button is released, this communicates the mouse is no longer being clicked to PlayerManager, which will then
	 * notify the engine that the mouse click has ceased.
	 * 
	 * @param x - X coordinate 
	 * @param y - Y coordinate 
	 */
	public void setPrimaryButtonUp(double x, double y) {
		primaryButtonDown = false;
	}
	
	/**
	 * Gets the x position of a mouse click for the engine to know where the click occurred and accordingly handle the event based on the
	 * conditions and actions defined by the game creator in the authoring environment.
	 * 
	 * @return the x position of a mouse click
	 */
	public double getClickX() {
		return clickX;
	}
	
	/**
	 * Gets the y position of a mouse click for the engine to know where the click occurred and accordingly handle the event based on the
	 * conditions and actions defined by the game creator in the authoring environment.
	 * 
	 * @return the y position of a mouse click
	 */
	public double getClickY() {
		return clickY;
	}
	
	/**
	 * Checks to see if a button is currently down so the engine handles the button input.
	 * 
	 * @return if a button is currently down
	 */
	public boolean isPrimaryButtonDown() {
		return primaryButtonDown;
	}
	
	/**
	 * Checks to see if there was a button pressed in the last frame of the step so that the engine knows to handle a button being held down.
	 * 
	 * @return if there is a button pressed in the past frame of the step
	 */
	public boolean isPrevPrimaryButtonDown() {
		return prevPrimaryButtonDown;
	}
	
	/**
	 * Sets the instance of the player in PlayerManager to allow communication between the player and engine.
	 * 
	 * @param currentGameDisplay - The instance of GameDisplay
	 */
	public void setDisplay(GameDisplay currentGameDisplay) {
		gameDisplay = currentGameDisplay;
	}
	
	/**
	 * Updates the set of previous keys and buttons after a step is finished in the engine.
	 */
	public void step() {
		prevKeysDown = new HashSet<>(keysDown);
		prevPrimaryButtonDown = primaryButtonDown;
	}
		
	public String getTyped() {
		return typed;
	}
	
	public void clearTyped() {
		typed = "";
	}
	
	/**
	 * Passes the images added to the game maps in authoring to the Game Display.
	 * 
	 * @param imageData - The list of images to be displayed
	 */
	public void setImageData(List<Displayable> images) {
		gameDisplay.setUpdatedDisplayables(images);
	}
	
	/**
	 * Sets the engine controller from GameController so the PlayerManager can stop the engine when a user stops playing a game.
	 * 
	 * @param currentEngineController - The instance of EngineController
	 */
	public void setEngineController(EngineController currentEngineController) {
		engineController = currentEngineController;
	}
	
	/**
	 * Stops the engine step from running; this is useful when the user exits a game.
	 */
	public void stop() {
		engineController.stop();
	}

	/**
	 * Sets the current position of the mouse.
	 * 
	 * Called by GameDisplay any time the mouse moves.
	 * 
	 * @param x
	 * @param y
	 */
	public void setMouseXY(double x, double y) {
		mouseX = x;
		mouseY = y;
	}
	
	/**
	 * Returns a vector of the xy-coordinates of the mouse.
	 * 
	 * @return Vector of xy-coordinates of the mouse.
	 */
	public Point2D getMouseXY() {
		return new Point2D(mouseX, mouseY);
	}

	public void save() {
		engineController.stop();
		engineController.setPlayerManager(null);
		gameDataHandler.saveForContinue(engineController);
		engineController.setPlayerManager(this);
		engineController.start();
	}

	public void exitToMenu() {
		gameDisplay.exitToMenu();
	}

	public DataView getDataView() {
		return dataView;
	}
	
}
