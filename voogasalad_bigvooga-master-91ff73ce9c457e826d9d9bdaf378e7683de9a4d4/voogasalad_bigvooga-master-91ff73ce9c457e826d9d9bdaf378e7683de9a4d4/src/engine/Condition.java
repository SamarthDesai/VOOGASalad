package engine;

import engine.operations.booleanops.BooleanOperation;

/**
 * Implementations of Condition represent any instances for which an object is defined to carry
 * out Actions. These Conditions check for three main types of instances: user input, variable states,
 * and states of other Conditions.
 * 
 * Conditions such as KeyHeld and ScreenClicked check if a requested type of user input, from the
 * keyboard or the mouse for example, occurs in a given game step. Conditions such as DoubleGreaterThan
 * and StringEquals check if a variable of a certain type is in a requested state. Conditions such as
 * And, Or, and Not check the states of other Conditions, allowing for the user to combine multiple
 * Conditions to create new ones.
 * 
 * Users map a Condition to a list of Actions to define the behavior of an object.
 * 
 * 
 * @author Aaron Paskin
 *
 */
public class Condition implements Comparable<Condition> {
	
	public int priorityNum;
	private BooleanOperation operation;
	
	public Condition(int priorityNum, BooleanOperation operation) {
		this.operation = operation;
		this.priorityNum = priorityNum;
	}
	
	/**
	 * Returns true when requested states of asking, world, and/or given Conditions occur.
	 * 
	 * @param asking
	 * @param world
	 * @return
	 */
	public boolean isTrue(GameObject asking, GameObjectEnvironment world) {
		return operation.evaluate(asking, world);
	}
	
	/**
	 * Returns the priority number of the Condition, which is used to customize the order
	 * in which Conditions are evaluated each step.
	 * @return
	 */
	public int getPriority() {
		return priorityNum;
	}
	
	@Override
	public int compareTo(Condition o) {
		return priorityNum - o.getPriority();
	}
	
}
