package engine.Actions.changeObject;

import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;
import engine.operations.vectorops.VectorOperation;

public class AdvancedCreate extends Create {

	public AdvancedCreate(
			@VoogaAnnotation(name = "Sprite Name", type = VoogaType.STRING) StringOperation name,
			@VoogaAnnotation(name = "Starting Location", type = VoogaType.VECTOR) VectorOperation location,
			@VoogaAnnotation(name = "Starting Heading", type = VoogaType.DOUBLE) DoubleOperation heading) {
		super(name, location, heading);
		// TODO Auto-generated constructor stub
	}

}
