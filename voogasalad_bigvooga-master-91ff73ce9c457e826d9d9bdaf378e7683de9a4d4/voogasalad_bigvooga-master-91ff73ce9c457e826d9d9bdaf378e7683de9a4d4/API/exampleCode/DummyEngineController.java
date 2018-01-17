package exampleCode;

import java.util.HashSet;
import java.util.Set;

import engine.EngineController;
import engine.GameObject;
import engine.World;
import javafx.scene.input.KeyCode;

public class DummyEngineController implements EngineController{

	//Made package friendly for building stand-alone examples
	Set<KeyCode> pressed = new HashSet<>();
	private World currentWorld;	
	
	public DummyEngineController() {
		
	}
	
	@Override
	public void start() {
		
	}
	
	//Example
	private void step() {
		for(GameObject object : currentWorld) {
			object.step();
		}
	}

	@Override
	public void addListener(Runnable listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWorld(World w) {
		// TODO
	}
}
