package exampleCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import engine.Action;
import engine.Condition;
import engine.GameObject;

public class DummyObject implements GameObject {

	Map<Condition, List<Action>> events = new HashMap<>();
	
	@Override
	public void addConditionAction(Condition c, Action a) {
		if(!events.containsKey(c))
			events.put(c, new ArrayList<>());
		events.get(c).add(a);
	}

	@Override
	public void step() {
		for(Entry<Condition, List<Action>> e : events.entrySet()) {
			if(e.getKey().isTrue(this)) {
				for(Action a : e.getValue())
					a.execute(this);
			}
		}
	}
	
	@Override
	public void addTag(String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean is(String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGlobal(String variableName, boolean global) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeAllGlobal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIntegerVariable(String name, int val) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDoubleVariable(String name, double val) {
		// TODO Auto-generated method stub
		;
	}

	@Override
	public void setStringVariable(String name, String val) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIntegerVariable(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleVariable(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringVariable(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToObjectList(GameObject o) {
		// TODO Auto-generated method stub

	}
}
