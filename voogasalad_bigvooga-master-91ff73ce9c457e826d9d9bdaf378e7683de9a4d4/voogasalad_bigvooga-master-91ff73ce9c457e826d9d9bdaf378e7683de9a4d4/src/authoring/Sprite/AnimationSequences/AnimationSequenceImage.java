package authoring.Sprite.AnimationSequences;

import engine.sprite.BoundedImage;
import engine.utilities.data.GameDataHandler;
import javafx.scene.layout.StackPane;

public class AnimationSequenceImage {
	
	private AuthoringImageView myImage;

	AnimationSequenceImage(AuthoringImageView AEI){
		myImage = AEI;
	}
	
	AnimationSequenceImage(AnimationSequenceImage ASE){
		myImage = new AuthoringImageView(ASE.getImage()); 
	}
	
	public AuthoringImageView getImage(){
		return myImage;
	}
	
	public void setGameDataHandler(GameDataHandler GDH){
		myImage.setGameDataHandler(GDH);
	}
	
//	public StackPane getThumbnail(){
//		StackPane SP = new StackPane();
//		SP.getChildren().add(myImage);
//		return SP;
//	}
}
