package authoring.drawing.selectors;

import java.util.function.Consumer;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * Allows Colors to be chosen and notifies listener.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ColorSelector extends ColorPicker {
	private static final Color DEFAULT_COLOR = Color.BLACK;
	
	/**
	 * @param listener Notified when the color changes.
	 */
	public ColorSelector(Consumer<Color> listener) {
		this(listener, DEFAULT_COLOR);
	}
	
	public ColorSelector(Consumer<Color> listener, Color startingColor) {
		super(startingColor);
		setOnAction(e -> listener.accept(getValue()));
		listener.accept(getValue());
	}
}
