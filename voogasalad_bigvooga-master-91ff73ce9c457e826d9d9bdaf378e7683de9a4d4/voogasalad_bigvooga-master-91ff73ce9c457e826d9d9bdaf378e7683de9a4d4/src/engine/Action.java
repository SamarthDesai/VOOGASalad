package engine;

/**
 * 
 * Implementations of Action represent any change in the state of a game. These Actions serve three main
 * purposes: changing object variables, changing gloabal variables and states, and executing other actions.
 * 
 * Actions such as ChangeDouble and ChangeString set the values of variables associated with requested
 * objects, or with itself by default. Actions such as ChangeWorld, Create, and Destroy change the state
 * of the game beyond the scope of single objects. They might transition to a different map or create and
 * destroy other objects in the game. Actions such as DoTimes execute other actions in a requested order 
 * and/or a requested amount of times; they can act as loops.
 * 
 * Users map a Condition to a list of Actions to define an object's behavior.
 * 
 * @author Aaron Paskin
 *
 */
public interface Action{
	
	/**
	 * Executes the Action by modifying asking and/or world.
	 * 
	 * @param asking
	 * @param world
	 */
	public void execute(GameObject asking, GameObjectEnvironment world);
	
}
