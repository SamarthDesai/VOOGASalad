package authoring;

import java.util.ArrayList;

/**
 * Class for keeping track of sprite names
 * 
 * @author taekwhunchung
 *
 */

public class SpriteNameManager {

	private ArrayList<String> myTemplateNames;
	private ArrayList<String> myInstanceNames;

	public SpriteNameManager() {
		myTemplateNames = new ArrayList<String>();
		myInstanceNames = new ArrayList<String>();
	}
	public void addTemplateName(String name) {
		myTemplateNames.add(name);
	}
	
	public void addInstanceName(String name) {
		myInstanceNames.add(name);
	}

	/**
	 *returns True if that template name already exists. 
	 * @param name
	 * @return
	 */
	
	public boolean isNameValidTemplate(String name) {
		return !(myTemplateNames.contains(name))&&name.replaceAll("\\s+", "").length()!=0;
	}
	
	/**
	 *returns True if that instance name already exists. 
	 * @param name
	 * @return
	 */
	
	public boolean isNameValidInstance(String name) {
		return !(myInstanceNames.contains(name));
	}
}
