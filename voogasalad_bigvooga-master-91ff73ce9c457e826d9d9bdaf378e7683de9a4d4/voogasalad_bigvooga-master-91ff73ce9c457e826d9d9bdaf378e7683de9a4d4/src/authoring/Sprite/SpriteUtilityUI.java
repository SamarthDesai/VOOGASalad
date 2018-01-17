package authoring.Sprite;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SpriteUtilityUI extends HBox {

	private int value;

	SpriteUtilityUI() {

	}

	SpriteUtilityUI(Pane p) {
		addChild(p);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int newVal) {
		value = newVal;
	}

	public void addChild(Pane p) {
		this.getChildren().add(p);
	}

}
