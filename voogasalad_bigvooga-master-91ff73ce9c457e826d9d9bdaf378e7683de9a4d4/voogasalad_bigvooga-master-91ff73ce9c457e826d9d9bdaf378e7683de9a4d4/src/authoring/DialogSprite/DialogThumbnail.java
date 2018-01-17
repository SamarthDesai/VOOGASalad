package authoring.DialogSprite;

import authoring.Thumbnail;
import javafx.scene.image.ImageView;

public class DialogThumbnail extends Thumbnail{
	
	private AuthoringDialogSequence myASO;
	private AuthoringDialogSequence myASOCopy;
	
	public DialogThumbnail(ImageView im, String name){
		super(im, name);
	}
	
	public DialogThumbnail(AuthoringDialogSequence ASO, Boolean showButton){
		this(ASO.getImage(), ASO.getName());
		myASO = ASO;
	}
	
	public DialogThumbnail(AuthoringDialogSequence ASO){
		this(ASO, false);
	}
	
	public AuthoringDialogSequence getSprite(){
		return myASO;
	}

}
