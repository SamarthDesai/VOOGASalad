package authoring.CutScene;

import authoring.Thumbnail;
import javafx.scene.image.ImageView;

public class SuperlayerThumbnail extends Thumbnail{
	
	private SuperlayerSequence myASO;
	private SuperlayerSequence myASOCopy;
	
	public SuperlayerThumbnail(ImageView im, String name){
		super(im, name);
	}
	
	public SuperlayerThumbnail(SuperlayerSequence ASO, Boolean showButton){
		this(ASO.getImage(), ASO.getName());
		myASO = ASO;
	}
	
	public SuperlayerThumbnail(SuperlayerSequence ASO){
		this(ASO, false);
	}
	
	public SuperlayerSequence getSuperlayerSequence(){
		return myASO;
	}

}
