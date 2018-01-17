package authoring.drawing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Uses a properties file to generate the known drawing tools for use by the canvas. 
 * 
 * Assumes all DrawingTools have a constructor with parameters (String, ImageCanvas).
 * 
 * Assumes the properties file gives the relative location of the tools.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ToolFactory {

	/**
	 * @param imageCanvas The imageCanvas these tools will work on.
	 * @param bundle Of the known tools
	 * @return All known tools, initialized to use the imageCanvas.
	 */
	public static List<DrawingTool> getTools(ImageCanvas imageCanvas, ResourceBundle bundle) {
		Enumeration<String> toolNames = bundle.getKeys();
		List<DrawingTool> instances = new ArrayList<>();
		while (toolNames.hasMoreElements()) {
			String tool = toolNames.nextElement();
			try {
				DrawingTool dTool = (DrawingTool) Class.forName(classNameFromRelative(tool)).getDeclaredConstructor(String.class, ImageCanvas.class)
						.newInstance(bundle.getString(tool), imageCanvas);
				instances.add(dTool);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				throw new PaintException("drawingTools.properties File is Misformatted");
			}
		}
		return instances;
	}

	private static String classNameFromRelative(String tool) {
		return ToolFactory.class.getPackage().getName()+"."+tool;
	}

}
