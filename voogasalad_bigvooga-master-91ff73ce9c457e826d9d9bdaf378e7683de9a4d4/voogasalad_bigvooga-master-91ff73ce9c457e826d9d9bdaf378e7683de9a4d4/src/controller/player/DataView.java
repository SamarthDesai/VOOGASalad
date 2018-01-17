package controller.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import engine.GameObject;
import javafx.geometry.Point2D;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class DataView {

	private Map<String, List<String>> objectMapping;

	public DataView() {
		objectMapping = new TreeMap<String, List<String>>(new varCompare());
		
	}

	private void gatherObjectData(List<GameObject> allGameObjects) {
		int iter = 0;
		for (GameObject object : allGameObjects) {
			// List<String> holdableNames = new ArrayList<>();
			// for(Holdable h : object.getInventory().getFullInventory()) {
			// holdableNames.add(h.getName());
			// }
			List<String> objProperties = display(object.getName(), object.getTags(), object.getLocation(), object.getHeading(),
					object.getAllDoubleVars(), object.getAllStringVars(), object.getAllBooleanVars());
			objectMapping.put(""+iter+": "+object.getName(), objProperties);
			iter++;
		}
		
	}

	public Map<String, List<String>> getData(List<GameObject> allGameObjects) {
		gatherObjectData(allGameObjects);
		return objectMapping;
	}

	
	private List<String> display(String name, Set<String> tags, Point2D pos, double heading,  Map<String, Double> allDoubleVars,
			Map<String, String> allStringVars, Map<String, Boolean> allBooleanVars) {
		// Includes variable names concatenated with values as a string
		List<String> titleAndValue = new ArrayList<String>();
		titleAndValue.add("Name: " + name);
		titleAndValue.add("Tags: " + tags.toString());
		titleAndValue.add("X coordinate: "+ pos.getX());
		titleAndValue.add("Y coordinate: "+ pos.getY());
		titleAndValue.add("Direction: " + heading);
		addMapValues(titleAndValue, allDoubleVars);
		addMapValues(titleAndValue, allStringVars);
		addMapValues(titleAndValue, allBooleanVars);

		return titleAndValue;

	}

	private void addMapValues(List<String> l, Map<String, ?> vars) {
		for (String s : vars.keySet()) {
			l.add(s + ": " + vars.get(s).toString());
		}
	}
	
	/**
	 * Sorts Objects based on their int ordering.
	 * @author Nikolas Bramblett
	 *
	 */
	private class varCompare implements Comparator<String>
	{

		@Override
		public int compare(String arg0, String arg1) {
			// TODO Auto-generated method stub
			String sub1 = null;
			String sub2 = null;
			for(int i = 0; i < arg1.length(); i++)
			{
				if(!Character.isDigit(arg0.charAt(i)))
				{
					sub1 = arg0.substring(0,  i);
					break;
				}
			}
			for(int i = 0; i < arg1.length(); i++)
			{
				if(!Character.isDigit(arg1.charAt(i)))
				{
					sub2 = arg1.substring(0,  i);
					break;
				}
			}
			return Integer.parseInt(sub1) - Integer.parseInt(sub2);
		}
		
	}

}
