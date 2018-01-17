package authoring.Sprite;

import authoring.Thumbnail;
import javafx.scene.image.ImageView;

public class SpriteThumbnail extends Thumbnail{
	
	private AbstractSpriteObject myASO;
	private AbstractSpriteObject myASOCopy;
	
	public SpriteThumbnail(ImageView im, String name){
		super(im, name);
	}
	
	public SpriteThumbnail(AbstractSpriteObject ASO, Boolean showButton){
		this(ASO, ASO.getName());
		myASO = ASO;
	}
	
	public SpriteThumbnail(AbstractSpriteObject ASO){
		this(ASO, false);
	}
	
	public AbstractSpriteObject getSprite(){
//		if (myASOCopy==null){
//			myASOCopy = myASO.newCopy();
//		}
		return myASO;
	}

}
