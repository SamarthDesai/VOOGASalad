package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;

public class CharAt implements StringOperation{

	private StringOperation string; 
	private DoubleOperation charAt;
	public CharAt(@VoogaAnnotation(name = "String", type = VoogaType.STRING) StringOperation string, @VoogaAnnotation(name = "Index", type = VoogaType.DOUBLE) DoubleOperation charAt) {
		this.string = string;
		this.charAt = charAt;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return ""+string.evaluate(asking, world).charAt(charAt.evaluate(asking, world).intValue());
	}

}
