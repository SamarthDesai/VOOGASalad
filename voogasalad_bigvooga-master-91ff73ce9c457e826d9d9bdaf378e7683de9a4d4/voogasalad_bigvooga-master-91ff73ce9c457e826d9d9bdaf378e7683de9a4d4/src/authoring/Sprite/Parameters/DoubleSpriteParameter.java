package authoring.Sprite.Parameters;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import javafx.application.Platform;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;

public class DoubleSpriteParameter extends SpriteParameter {
	
	Double myValue; 
	
	DoubleSpriteParameter(){
		
	}
	
	DoubleSpriteParameter(String name, Object value){
		super(name, value);
		}

	@Override
	public void updateValue(Object value) {
		if (value instanceof String){
			try {
				myValue = Double.parseDouble((String) value);
				;
			} catch (Exception e){
				throw e;
			}
		} else if (value instanceof Double) {
			myValue = (Double) value;
		} else if (value instanceof Integer) {
			myValue = (Double) value;
		} else {
			throw new RuntimeException("Double Parameter must have a double as a value.");
		}
		
	}



	@Override	
	protected void setUpVariables(String name, Object in){
		if (!(in instanceof Double)){
			throw new RuntimeException("Double Parameter must have a double as a value.");
		}
		myValue = (Double) in;
		myName = name;
	}


	@Override
	public Object getValue() {
		return myValue;
	}


	@Override
	public boolean checkError(Object value) throws Exception{
		
		if(!(value instanceof String)){
			throw new Exception("The input is not valid");
		}
		
		String valString = (String) value;
		valString = valString.trim();
		Double valDouble;
		
		try{
			if (!valString.equals(""))
			valDouble = Double.parseDouble(valString);
		} catch (Exception e){
			throw new Exception(String.format("The input is not a valid double.", valString));
		}
		
		return true;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(myName);
		out.writeObject(myValue);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		myName = (String)in.readObject();
		myValue = (Double)in.readObject();
	}
	
	public DoubleSpriteParameter newCopy(){
		return new DoubleSpriteParameter(new String(this.getName()), new Double(((Double)this.getValue())));
	}
	

}
