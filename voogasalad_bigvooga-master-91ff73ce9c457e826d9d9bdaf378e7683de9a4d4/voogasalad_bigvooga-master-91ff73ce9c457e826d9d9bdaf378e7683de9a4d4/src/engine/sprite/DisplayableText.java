package engine.sprite;

import gui.player.GameDisplay;

/**
 * Text that can be displayed. Holds the string to be displayed, the
 * font/size/color, and a relative position to be used when composing this with
 * other Displayables. 
 * 
 * @author Ian Eldridge-Allegra and Nikolas Bramblett
 *
 */
public class DisplayableText extends PositionableObject implements RelativeDisplayable {

	public static final DisplayableText DEFAULT = new DisplayableText(Integer.MAX_VALUE, "", "Comic Sans", 36,
			"rgb(255,255,255);");
	private String string;
	private String font;
	private double fontSize;
	private String color;
	private Positionable relativePosition;

	public DisplayableText(String string, String font, double fontSize, String webColor) {
		this(Integer.MAX_VALUE, string, font, fontSize, webColor);
	}

	public DisplayableText(int drawingPriority, String string, String font, double fontSize, String webColor) {
		super(drawingPriority);
		setRelativePosition(new PositionableObject());
		this.string = string;
		this.font = font;
		this.fontSize = fontSize;
		color = webColor;
	}

	@Override
	public void visit(GameDisplay display) {
		display.displayText(this);
	}

	public String getText() {
		return string;
	}

	public String getFont() {
		return font;
	}

	public double getFontSize() {
		return fontSize;
	}

	public String getColor() {
		return color;
	}

	@Override
	public void setRelativePosition(Positionable p) {
		this.relativePosition = p;
	}

	@Override
	public Positionable getRelativePosition() {
		return relativePosition;
	}

	public DisplayableText getWithMessage(String dialogue) {
		return new DisplayableText(getDrawingPriority(), dialogue, font, fontSize, color);
	}

}
