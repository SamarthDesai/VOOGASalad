package engine;
import java.util.List;

public interface GameObject {
	//private List<String> tags
	//private Map<Condition, List<Action>> events
	//private Map<String, Integer> intVars
	//private Map<String, Double> doubleVars
	//private Map<String, String> StringVars
	public void addTag(String tag);
	public boolean is(String tag);
	public List<String> getTags();
	public void setGlobal(String variableName, boolean global);
	public void makeAllGlobal();
	public void setIntegerVariable(String name, int val);
	public void setDoubleVariable(String name, double val);
	public void setStringVariable(String name, String val);
	public int getIntegerVariable(String name);
	public double getDoubleVariable(String name);
	public String getStringVariable(String name);
	public void addToObjectList(GameObject o);
	public void addConditionAction(Condition c, Action a);
	public void step();
}
