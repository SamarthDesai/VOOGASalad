package engine;
/**
* Condition returns true, the Condition tells the GameObject to execute the actions associated with this condition.
*/
public interface Condition{
	public boolean isTrue(GameObject asking);
}
