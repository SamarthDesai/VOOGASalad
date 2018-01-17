package authoring.Sprite.Parameters;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BooleanSpriteParameter extends SpriteParameter {
	
	Boolean myValue;
	
	BooleanSpriteParameter(){
		
	}
	
	
	BooleanSpriteParameter(String name, Object checkedStatus){
		super(name, checkedStatus);
	}
	

	
	protected void setUpVariables(String name, Object in){
		myValue = (boolean) in;
		myName = name;
	}
	
	@Override
	public void updateValue(Object value) {
		myValue = (Boolean) value;
		
	}

	@Override
	public Object getValue() {	
		return myValue;
	}

	@Override
	public boolean checkError(Object value) throws Exception {
		if (!(value instanceof Boolean)){
			throw new Exception("Boolean Parameters must have Boolean values!!");
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
		myValue = (Boolean)in.readObject();
	}
	
	public BooleanSpriteParameter newCopy(){
		return new BooleanSpriteParameter(new String(this.getName()), new Boolean(((Boolean)this.getValue())));
	}


}
