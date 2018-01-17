package player;

import java.util.List;

import engine.GameObject;

/**
 * View displays all game objects and communicates any user key/mouse inputs to
 * the EngineController
 */
public interface View {

	/**
	 * Called when EngineController sends a list of changed objects that need to be
	 * displayed
	 *
	 * @ par changedObjects is a list of objects whose changes need to be displayed.
	 */
	public void update(List<VariableContainer> changedObjects);
	
	/**
	 * Called when the Player simulation is started.
	 */
	public void start();
	
	/**
	 * Adds the controller (between view and model/engine) as a listener
	 * @param listener
	 */
	public void addListener(Runnable listener);
}
