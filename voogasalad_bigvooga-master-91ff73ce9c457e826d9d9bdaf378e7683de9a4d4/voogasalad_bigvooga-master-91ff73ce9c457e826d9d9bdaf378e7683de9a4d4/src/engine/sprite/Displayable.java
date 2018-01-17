package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public interface Displayable extends Positionable {
	public void visit(GameDisplay display);
	public int getDrawingPriority();
}
