package ActionConditionClasses;

import java.util.ResourceBundle;

/**
 * ResourceBundleUtil--a class that has a couple of useful static methods for accessing text resources related to action and condition tabs
 * Assumptions: the directory for action and condition tabs and the resource bundle for that path will not change while the user is authoring or playing
 * the game, since everything is static in it. Also assumes that the resource files contain strings 
 * Dependencies: the resourceBundle util package
 * Example of how to use it: call either one of its methods
 * @author Owen Smith
 *
 */

public class ResourceBundleUtil {
	
	private static final String conditionActionTabTitlePath = "TextResources/ConditionActionTitles";
	private static final ResourceBundle conditionActionTabResource = ResourceBundle.getBundle(conditionActionTabTitlePath);
	
	/**
	 * getTabTitle(String key)--returns the title of the tab given the key provided
	 * @param key--the tab title that the user wants to get
	 * @return--the string value of the tab title the user desired
	 */
	public static String getTabTitle(String key) {
		return conditionActionTabResource.getString(key);
	}
	
	/**
	 * getResourcBundle--gets a resource bundle given the String tab title
	 * @param tabTitle--the tab title
	 * @return--the resourceBundle corresponding to that tab title
	 */
	public static ResourceBundle getResourceBundle(String tabTitle) {
		String tabResourcePath = "TextResources/" + tabTitle.substring(0,tabTitle.length() - 1) + "TabResources";
		return ResourceBundle.getBundle(tabResourcePath);
	}

}
