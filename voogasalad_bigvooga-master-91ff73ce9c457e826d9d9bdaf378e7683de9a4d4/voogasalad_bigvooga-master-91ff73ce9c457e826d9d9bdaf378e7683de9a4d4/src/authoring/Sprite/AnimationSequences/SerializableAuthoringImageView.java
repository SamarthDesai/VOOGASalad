package authoring.Sprite.AnimationSequences;


import authoring.Sprite.AnimationSequences.AuthoringImageView;
import engine.sprite.BoundedImage;
import javafx.scene.image.Image;

public class SerializableAuthoringImageView {
	
	private String imagePath;
	private BoundedImage bImage;
	
	
	public SerializableAuthoringImageView(AuthoringImageView AIV){
		imagePath = AIV.getImagePath();
		bImage = AIV.getBoundedImage();
	}
	
	private Object readResolve() throws java.io.ObjectStreamException{
        return new AuthoringImageView(imagePath, bImage);  
}

}
