package authoring.drawing.selectors;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.control.ChoiceBox;

/**
 * Allows Stroke to be chosen and notifies listener.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class StrokeSelector extends ChoiceBox<Integer>{
	
	private static final int DEFAULT_STROKE_INDEX = 9;
	public static final Integer[] DEFAULT_STROKES =  { 3,4,5,6,7,8,9,10,11,12,14,16,18,20,22,24,26,30,36,48,64 };
	
	/**
	 * @param listener Notified when the stroke changes
	 */
	public StrokeSelector(Consumer<Integer> listener) {
		super();
		setStrokes(Arrays.asList(DEFAULT_STROKES), DEFAULT_STROKE_INDEX);
		setOnAction(e->listener.accept(getValue()));
		listener.accept(getValue());
	}
	
	/**
	 * Sets the possible choices, replacing the previous ones.
	 * 
	 * @param strokeValues Possible choices
	 * @param startingIndex Starting index of choices
	 */
	public void setStrokes(List<Integer> strokeValues, int startingIndex) {
		getItems().clear();
		getItems().addAll(strokeValues);
		getSelectionModel().select(startingIndex);
	}
	
}
