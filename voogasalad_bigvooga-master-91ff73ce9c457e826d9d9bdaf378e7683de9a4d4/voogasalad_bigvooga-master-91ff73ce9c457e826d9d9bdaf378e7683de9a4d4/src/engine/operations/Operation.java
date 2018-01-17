package engine.operations;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * An operation is simply something that returns a specific type when evaluated
 * by a GameObject (the asking object) in a particular set of conditions (the
 * environment). Extending interfaces were included to make the reflection/code
 * simpler. The OperationFactory includes more detail about what is required in
 * the constructors of operations, but the idea is that they take in other
 * operations, evaluate them, and use the results. This produces a clean
 * hierarchy that is similar in some respects to Slogo, but makes use of strict
 * types to avoid some of the weird aspects of that language while also being
 * more flexible/complex.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public interface Operation<T> {
	public T evaluate(GameObject asking, GameObjectEnvironment world);
}
