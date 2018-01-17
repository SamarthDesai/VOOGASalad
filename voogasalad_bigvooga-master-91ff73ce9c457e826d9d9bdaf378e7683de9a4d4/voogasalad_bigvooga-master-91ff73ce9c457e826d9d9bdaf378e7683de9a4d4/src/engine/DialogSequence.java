package engine;

import java.util.ArrayList;
import java.util.List;

import engine.Actions.dialog.CloseDialog;
import engine.Actions.dialog.DisplayDialog;
import engine.operations.booleanops.ObjectClicked;
import engine.operations.gameobjectops.Self;
import engine.operations.stringops.SelfString;
import engine.operations.vectorops.LocationOf;
import engine.sprite.AnimationSequence;
import engine.sprite.BoundedImage;
import engine.sprite.CompositeImage;
import engine.sprite.Displayable;
import engine.sprite.DisplayableText;
import engine.sprite.Sprite;

/**
 * A rather awkward, hacky way to connect the dislog sequences in authoring to
 * those in the engine. It does the basic work of constructing a GameObject with
 * a background image and the requisite conditions/actions to support
 * changing/closing the dialog when clicked. The only additional support
 * provided here is the ability to impose images on top of the object, since
 * text was already supported in GameObject.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class DialogSequence extends GameObject {
	public DialogSequence next;
	private List<BoundedImage> images;

	public DialogSequence(String name, String imageForBackground, List<DisplayableText> text) {
		super(name);
		setDialogue(text);
		List<BoundedImage> im = new ArrayList<>();
		im.add(new BoundedImage(imageForBackground));
		AnimationSequence s = new AnimationSequence("Anim", im);
		Sprite sprite = new Sprite();
		sprite.addAnimationSequence(s);
		sprite.setAnimation("Anim");
		setSprite(sprite);
		List<Action> actions = new ArrayList<>();
		actions.add(new CloseDialog(new SelfString(getName())));
		addConditionAction(new Condition(0, new ObjectClicked(new Self())), actions);
	}

	/**
	 * @param next
	 *            The next dialog in the sequence -- switched to when this is
	 *            clicked.
	 */
	public void setNextDialog(DialogSequence next) {
		this.next = next;
		List<Action> actions = new ArrayList<>();
		actions.add(new DisplayDialog(new SelfString(next.getName()), new LocationOf(new Self())));
		addConditionAction(new Condition(0, new ObjectClicked(new Self())), actions);
	}

	/**
	 * @param images
	 *            The images to be placed on top of this, which are given with
	 *            relative coordinates/sizes to this one.
	 */
	public void imposeImages(List<BoundedImage> images) {
		this.images = images;
	}

	@Override
	public Displayable getDisplayable() {
		return new CompositeImage(new CompositeImage(getBounds(), dialogueHandler), images);
	}
}
