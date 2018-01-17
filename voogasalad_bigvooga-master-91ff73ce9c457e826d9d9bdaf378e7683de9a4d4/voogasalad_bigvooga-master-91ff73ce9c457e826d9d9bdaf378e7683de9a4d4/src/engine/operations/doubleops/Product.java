package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Product implements DoubleOperation {

	private DoubleOperation first;
	private DoubleOperation second;

	public Product(@VoogaAnnotation(name = "First", type = VoogaType.DOUBLE) DoubleOperation first, @VoogaAnnotation(name = "Second", type = VoogaType.DOUBLE) DoubleOperation second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world)*second.evaluate(asking, world);
	}

}
