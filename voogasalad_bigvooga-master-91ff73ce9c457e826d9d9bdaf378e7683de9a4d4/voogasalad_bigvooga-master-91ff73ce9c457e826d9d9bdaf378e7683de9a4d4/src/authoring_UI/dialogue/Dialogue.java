package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;
import authoring_UI.displayable.Displayable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class holding dialogue data.
 * 
 * @author DavidTran and Dara Buggay
 *
 */
public class Dialogue extends Displayable {

	public Dialogue(String name, String fontType, Color fontColor, List<Pane> dialogueSequence) {
		super(name, fontType, fontColor, dialogueSequence);
	}
	
	protected String getName() {
		return super.getName();
	}
	
	public List<Pane> getDialogueSequence() {
		return super.getDisplayableSequence();
	}

	protected String getFontType() {
		return super.getFontType();
	}
	
	protected Color getFontColor() {
		return super.getFontColor();
	}

}
