package exampleCode;

import java.util.function.Predicate;

import engine.Condition;
import engine.GameObject;
import javafx.scene.input.KeyCode;

public class IsKeyPressed implements Condition {

	private Predicate<KeyCode> checkForPressed;
	private KeyCode code;
	
	public IsKeyPressed(KeyCode code, Predicate<KeyCode> checkForPressed) {
		this.code = code;
		this.checkForPressed = checkForPressed;
	}
	
	@Override
	public boolean isTrue(GameObject asking) {
		return checkForPressed.test(code);
	}

}
