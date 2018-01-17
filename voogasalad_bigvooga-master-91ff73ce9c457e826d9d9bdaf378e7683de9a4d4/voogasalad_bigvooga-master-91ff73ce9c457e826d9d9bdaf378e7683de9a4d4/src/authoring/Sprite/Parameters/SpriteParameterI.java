package authoring.Sprite.Parameters;

import java.io.Externalizable;
import java.util.ArrayList;
import java.util.Observer;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

public interface SpriteParameterI extends Externalizable{
	
	void updateName(String name);
	String getName();
	void updateValue(Object value);
	Object getValue();
	boolean isSame(SpriteParameterI other);
	void update(String name, Object value);
	boolean checkError(Object value) throws Exception;
	SpriteParameter newCopy();
	
}
