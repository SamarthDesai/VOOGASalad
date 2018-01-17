package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;

public class SaveGame implements Action {

	private DoubleOperation currentPoints;

	public SaveGame(@VoogaAnnotation(name = "Current Score", type = VoogaType.DOUBLE) DoubleOperation currentPoints) {
		this.currentPoints = currentPoints;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.save(currentPoints);
	}

}
