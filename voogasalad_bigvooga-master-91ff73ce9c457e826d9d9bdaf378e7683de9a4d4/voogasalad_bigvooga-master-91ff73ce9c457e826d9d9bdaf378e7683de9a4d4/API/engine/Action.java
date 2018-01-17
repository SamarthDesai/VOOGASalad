package engine;
/**
* Action -- A general representation of any change in the game state
*/
public interface Action{
	void execute(GameObject asking);
}
