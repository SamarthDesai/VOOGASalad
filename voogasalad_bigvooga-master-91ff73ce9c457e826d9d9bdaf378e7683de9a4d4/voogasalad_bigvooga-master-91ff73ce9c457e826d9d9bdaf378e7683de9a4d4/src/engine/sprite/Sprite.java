package engine.sprite;

import java.util.HashMap;
import java.util.Map;

import engine.VoogaException;

/**
 * Holds multiple animation sequences and allows the animation to be set.
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public class Sprite {
	private Map<String, AnimationSequence> animations = new HashMap<String, AnimationSequence>();
	private AnimationSequence currentAnimation;
	private AnimationSequence tempAnimation = null;

	/**
	 * Adds the animation sequence to the known animations. If it is the first
	 * animation added, it will be used as the default.
	 * 
	 * @param animation
	 *            The new Animation sequence
	 */
	public void addAnimationSequence(AnimationSequence animation) {
		if (animations.size() == 0){
			currentAnimation = animation;
		}
		animations.put(animation.getName(), animation);
	}

	/**
	 * @param name
	 *            The new animation sequence to switch to, by name.
	 */
	public void setAnimation(String name) {
		if (!animations.containsKey(name))
			throw new VoogaException("AnimationNotFound", name);
		currentAnimation = animations.get(name);
		currentAnimation.reset();
	}

	/**
	 * Increments the AnimationSequences
	 */
	public void step() {
		if (tempAnimation != null) {
			tempAnimation.increment();
			if (tempAnimation.isDone())
				tempAnimation = null;
			return;
		}
		if (currentAnimation == null) {
			throw new VoogaException("UndefinedAnimation");
		}
		currentAnimation.increment();
	}

	/**
	 * @return Gets the current image.
	 */
	public BoundedImage getImage() {
		if (tempAnimation != null) {
			return tempAnimation.getImage();
		}
		if (currentAnimation == null)
			throw new VoogaException("UndefinedAnimation");
		return currentAnimation.getImage();
	}

	public Sprite clone() {
		Sprite clone = new Sprite();
		for (String s : animations.keySet()) {
			clone.addAnimationSequence(animations.get(s).clone());
		}
		clone.setAnimation(currentAnimation.getName());
		return clone;
	}

	/**
	 * @param name
	 *            The animation sequence to play once.
	 */
	public void playOnce(String name) {
		tempAnimation = animations.get(name);
	}

}
