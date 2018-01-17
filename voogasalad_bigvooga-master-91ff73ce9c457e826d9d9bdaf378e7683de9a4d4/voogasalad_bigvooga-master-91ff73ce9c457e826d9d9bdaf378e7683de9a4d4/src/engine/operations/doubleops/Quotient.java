package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Quotient implements DoubleOperation {

	private DoubleOperation denominator;
	private DoubleOperation numerator;

	public Quotient(@VoogaAnnotation(name = "Numerator", type = VoogaType.DOUBLE) DoubleOperation numerator,
			@VoogaAnnotation(name = "Denominator", type = VoogaType.DOUBLE) DoubleOperation denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return numerator.evaluate(asking, world) / denominator.evaluate(asking, world);
	}

}
