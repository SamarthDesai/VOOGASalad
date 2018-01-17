package authoring.Sprite.Parameters;

import java.util.Observable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public abstract class SpriteParameter implements SpriteParameterI {

	String myName;
	Object myValue;
	SpriteParameterI dummy; 
	boolean isDummy;
	
	SpriteParameter(){
		
	}
	
	
	
	SpriteParameter(String name, Object value){
		setMyValue(myValue);
		isDummy = false;
		setUpVariables(name, value);

	}
	
	protected abstract void setUpVariables(String name, Object value);
	
	@Override
	public void updateName(String name) {
		myName = name;
		
	}

	@Override
	public String getName() {
		return myName;
	}
	
	protected void setMyValue(Object in){
		myValue = in;
	}
	
	@Override
	public void update(String newName, Object newValue){
		updateName(newName);
		updateValue(newValue);
	}

	@Override
	public abstract void updateValue(Object value);

	@Override
	public abstract Object getValue();
	
	
	@Override 
	public boolean isSame(SpriteParameterI other) {
		return getName().equals(other.getName()) && getValue().equals(other.getValue());
	}
	
	@Override 
	public abstract boolean checkError(Object value) throws Exception;
	
	public abstract SpriteParameter newCopy();

}
