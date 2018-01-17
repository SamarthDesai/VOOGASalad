package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

public class MoveOnPath implements Action {

	private GameObjectOperation object;
	private DoubleOperation speed;

	public MoveOnPath(@VoogaAnnotation(name = "Sprite", type = VoogaType.GAMEOBJECT) GameObjectOperation object ,
			@VoogaAnnotation(name = "Speed", type = VoogaType.DOUBLE) DoubleOperation speed) {
		// TODO Auto-generated constructor stub
		this.object = object;
		this.speed = speed;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		// TODO Auto-generated method stub
		object.evaluate(asking, world).moveOnPath(speed.evaluate(asking, world));

	}

}
