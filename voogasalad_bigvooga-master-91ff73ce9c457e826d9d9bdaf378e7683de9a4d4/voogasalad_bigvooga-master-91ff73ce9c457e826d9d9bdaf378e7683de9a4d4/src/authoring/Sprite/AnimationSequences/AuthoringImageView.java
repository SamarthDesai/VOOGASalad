package authoring.Sprite.AnimationSequences;

import java.io.File;

import engine.sprite.BoundedImage;
import engine.utilities.data.GameDataHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuthoringImageView extends ImageView{
	
	private String imagePath;
	private BoundedImage boundedImage;
	
	public AuthoringImageView(String path){
		super();
//		File f = new File(path);
		imagePath = path;
	}
	
	public AuthoringImageView(String path, BoundedImage bImage){
		super();
//		File f = new File(path);
		boundedImage = bImage;
		imagePath = path;
	}
	
	public AuthoringImageView(String path, GameDataHandler GDH){
//		System.out.println("AIV path: "+path);
//		File f = new File(path);
		Image im = GDH.getImage(path);
		imagePath = path;
		setImage(im);
	}
	
	public AuthoringImageView(String path, BoundedImage bImage, GameDataHandler GDH){
		this(path, GDH);
		boundedImage = bImage;
	}
	
	public AuthoringImageView(AuthoringImageView image) {
		imagePath = new String(image.getImagePath());
		if(image.getBoundedImage() != null)
			boundedImage = image.getBoundedImage().clone();
		setImage(image.getImage());
	}

	public String getImagePath(){
		return imagePath;
	}
	
	public void setBoundedImage(BoundedImage BI){
		boundedImage = BI;
	}
	
	public BoundedImage getBoundedImage(){
		return boundedImage;
	}
	
	public void setGameDataHandler(GameDataHandler GDH){
		this.setImage(GDH.getImage(this.imagePath));
	}
	
	private Object writeReplace() throws java.io.ObjectStreamException {
		return new SerializableAuthoringImageView(this);
	}

}
