package engine.sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * A sequence of BoundedImages that loops.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class AnimationSequence{
	private static final int DEFAULT_STEPS_PER_FRAME = 20;
	private int imageIndex = 0;
	private List<BoundedImage> images;
	private String name;
	private int stepsPerFrame = DEFAULT_STEPS_PER_FRAME;
	private int stepsInCurrentFrame = 0;
	
	public AnimationSequence(String name, List<BoundedImage> images) {
		this.name = name;
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setStepsPerFrame(int n) {
		stepsPerFrame = n;
	}
	
	/**
	 * Move to the next image
	 */
	public void increment() {
		stepsInCurrentFrame++;
		if(stepsInCurrentFrame >= stepsPerFrame) {
			incrementImage();
			stepsInCurrentFrame = 0;
		}
	}
	
	private void incrementImage() {
		imageIndex++;
		if(imageIndex >= images.size())
			reset();
	}
	
	/**
	 * @return If the animation has reached its last step. 
	 */
	public boolean isDone() {
		return imageIndex == images.size()-1 && stepsInCurrentFrame == stepsPerFrame -1;
	}
	
	/**
	 * Move to the first image
	 */
	public void reset() {
		imageIndex = 0;
	}

	/**
	 * @return The BoundedImage of the current frame
	 */
	public BoundedImage getImage() {
		return images.get(imageIndex);
	}
	
	public AnimationSequence clone() {
		List<BoundedImage> clones = new ArrayList<>();
		for(BoundedImage i : images)
		{
			clones.add(i.clone());
		}
		return new AnimationSequence(name, clones);
	}
}
