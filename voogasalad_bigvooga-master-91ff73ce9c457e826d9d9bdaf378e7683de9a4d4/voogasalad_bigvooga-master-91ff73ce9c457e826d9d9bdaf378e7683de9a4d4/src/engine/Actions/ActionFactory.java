package engine.Actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import engine.Action;
import engine.VoogaException;
import engine.operations.OperationFactory;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaParameter;

/**
 * Responsible for constructing Actions and describing how to do so.
 * ActionFactories give what actions are available, describe the parameters
 * required for a particular Action, and construct Actions via reflection.
 * 
 * This class assumes:
 * 
 * 1. All Action categories are included in the Actions.properties file, mapped
 * to the relative locations of the properties file for that category.
 * 
 * 2. Each category has a properties file containing the descriptive name of all
 * actions of that category mapped to the full class name of their
 * implementation.
 * 
 * 3. All Action names are unique.
 * 
 * 4a. Each Action has only one constructor.
 * 
 * 4b. These constructors only take specific extensions of Operation which are
 * included in the properties for Operations (OR other Actions). 
 * 
 * 4c. Each parameter has an appropriate VoogaAnnotation. 
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ActionFactory {

	private static final String PACKAGE_NAME = ActionFactory.class.getPackage().getName();
	private static final String BUNDLE_LOCATION = PACKAGE_NAME + ".Actions";

	private Map<String, ResourceBundle> categoryBundles;
	private Map<String, String> operationTypeMap;

	public ActionFactory() {
		populateCategoryMap();
		operationTypeMap = OperationFactory.getParameterTypeMap();
	}

	private void populateCategoryMap() {
		categoryBundles = new HashMap<>();
		ResourceBundle bundleLocations = ResourceBundle.getBundle(BUNDLE_LOCATION);
		for (String category : bundleLocations.keySet()) {
			categoryBundles.put(category,
					ResourceBundle.getBundle(PACKAGE_NAME + "." + bundleLocations.getString(category)));
		}
	}

	/**
	 * @return The categories of Actions available. These are all the legal inputs
	 *         to getActions.
	 */
	public List<String> getCategories() {
		return new ArrayList<>(categoryBundles.keySet());
	}

	/**
	 * @param category
	 *            The category of Actions to list.
	 * @return All Actions available in the given category. These
	 */
	public List<String> getActions(String category) {
		return Collections.list(categoryBundles.get(category).getKeys());
	}

	/**
	 * @param actionName The name of the action (one of those given by getActions)
	 * @param parameters Whatever Operations/Actions are taken by the Action's constructor.
	 * @return The Action
	 */
	public Action makeAction(String actionName, Object... parameters) {
		try {
			return (Action) getConstructor(actionName).newInstance(parameters);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException e) {
			throw new VoogaException("ActionNotFound", actionName);
		}
	}

	/**
	 * @deprecated @see {@link #getParametersWithNames(String)}
	 */
	@Deprecated
	public List<String> getParameters(String actionName) {
		List<String> types = new ArrayList<>();
		try {
			for (Class<?> parameterType : getConstructor(actionName).getParameterTypes())
				types.add(operationTypeMap.get(parameterType.getSimpleName()));
		} catch (ClassNotFoundException e) {
			throw new VoogaException("ActionNotFound", actionName);
		}
		return types;
	}

	/**
	 * @param actionName The action name to get parameters from
	 * @return The parameters required by the action, with descriptive names of the parameters. 
	 */
	public List<VoogaParameter> getParametersWithNames(String actionName) {
		List<VoogaParameter> types = new ArrayList<>();
		try {
			for (Parameter parameter : getConstructor(actionName).getParameters()) {
				types.add(new VoogaParameter(parameter.getAnnotation(VoogaAnnotation.class).name(),
						parameter.getAnnotation(VoogaAnnotation.class).type()));
			}
		} catch (ClassNotFoundException e) {
			throw new VoogaException("ActionNotFound", actionName);
		}
		return types;
	}

	private Constructor<?> getConstructor(String actionName) throws ClassNotFoundException {
		return Class.forName(getClassName(actionName)).getDeclaredConstructors()[0];
	}

	private String getClassName(String actionName) {
		for (String key : categoryBundles.keySet()) {
			if (categoryBundles.get(key).containsKey(actionName))
				return categoryBundles.get(key).getString(actionName);
		}
		throw new VoogaException("ClassNotFoundFor", actionName);
	}
}
