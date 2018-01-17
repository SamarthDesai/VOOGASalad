package authoring.Sprite.Parameters;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class StringSpriteParameter extends SpriteParameter {
	
	String myValue; 
	
	StringSpriteParameter(){
		
	}
	
	public StringSpriteParameter(String name, Object value){
			super(name, value);	
		}
	

	@Override
	public void updateValue(Object value) {
		myValue = (String) value;
		
	}

	@Override
	public Object getValue() {
		return myValue;
	}


	@Override
	protected void setUpVariables(String name, Object value) {
		myValue = (String) value;
		myName = name;
	
	}


	@Override
	public boolean checkError(Object value) throws Exception {
		if(((String) value).matches(".*\\d+.*")){
			throw new Exception("The input is not a valid string.");
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
		myValue = (String)in.readObject();
	}
	
	public StringSpriteParameter newCopy(){
		return new StringSpriteParameter(new String(this.getName()), new String(((String)this.getValue())));
	}
}
