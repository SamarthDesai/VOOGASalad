package exampleCode;

import engine.GameObject;
import engine.World;
import javafx.scene.input.KeyCode;

public class ExampleCode {
	
	//Some of this logic would actually be in the enginecontroller, hence the package friendly field
	public static void example1() {
		DummyEngineController controller = new DummyEngineController();
		GameObject player = new DummyObject();
		player.addConditionAction(
				new IsKeyPressed(KeyCode.LEFT, (c)->controller.pressed.contains(c)),
				new MoveAction(-2, 0));
		World w = new DummyWorld();
		w.addGameObject(player);
		controller.addWorld(w);
	}
}
