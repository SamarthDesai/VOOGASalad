package gui.welcomescreen;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tools.DisplayLanguage;

/**
 * This class contains a collection of static methods that perform functions relevant throughout all the segments of the GUI.
 * @author Samarth Desai
 *
 */
public class GUITools {

	public GUITools() {
		
	}
	
	/**
	 * Creates an image using an image from the resources folder.
	 * 
	 * @param path - The directory path to access the image
	 * @param width - The width of the image being created
	 * @param height - The height of the image being created
	 * @return the newly created image
	 */
	public static Image createImage(String path, int width, int height) {
		Image image = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(path), width, height, true, true);
		return image;
	}
	
	/**
	 * Creates an ImageView given an image.
	 * 
	 * @param image - The image that will be converted to an ImageView
	 * @return the newly created ImageView
	 */
	public static ImageView createImageView(Image image) {
		ImageView imageView = new ImageView(image);
		return imageView;
	}
	
	/**
	 * Creates a label given the label text specifications
	 * 
	 * @param labelText - The text to be displayed
	 * @param font - The font of the text
	 * @param color - The color of the text
	 * @param size - The size of the label
	 * @return the generated label
	 */
	public static Label generateLabel(String labelText, String font, String color, String size) {
		Label label = new Label(labelText);
		label.textProperty().bind(DisplayLanguage.createStringBinding(labelText));
		label.setStyle(styleLabel(font, color, size));
		return label;
	}
	
	/**
	 * Styles the label with CSS
	 * 
	 * @param font - The font of the label text
	 * @param color - The color of the label text
	 * @param size - The size of the label text
	 * @return the CSS properties that will modify the label
	 */
	public static String styleLabel(String font, String color, String size) {
		return "-fx-font-family: " + font +
				"-fx-text-fill: " + color +  
				"-fx-font-size: " + size  
				;
	}
	
	/**
	 * Sets the border color of any box using CSS styling
	 * 
	 * @param color - the color to be applied to the border
	 * @return the border that is applied to the box
	 */
	public static String styleBox(String color) {
		return "-fx-border-style: solid inside;" + 
               "-fx-border-width: 2;" + 
               "-fx-border-radius: 5;" + 
               "-fx-border-color: " + color + ";";
	}
	
}
