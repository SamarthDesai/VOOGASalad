package authoring.CutScene;

import java.util.ArrayList;
import java.util.List;

import authoring.DialogSprite.SuperlayerAuthoringImage;
import authoring.DialogSprite.SuperlayerSprite;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class SuperlayerSequence {

	
	protected String myName;
	protected List<SuperlayerSprite> mySuperlayerSprites;
	
	public SuperlayerSequence(String sequenceName, List<Pane> panes, String paneURL){
		mySuperlayerSprites = new ArrayList<SuperlayerSprite>();
		panes.forEach((pane)->{
			mySuperlayerSprites.add(new SuperlayerSprite(pane, paneURL));
		});
		myName = sequenceName;
	}
	
	public SuperlayerSequence(){
		
	}
	
	public String getName(){
		return myName;
	}
	
	
	public ImageView getImage(){
		return new SuperlayerAuthoringImage(mySuperlayerSprites.get(0), ()->this);
	}
	
	public void setName(String name){
		myName = name;
	}
	
	public List<SuperlayerSprite> getSuperlayerSprites(){
		return mySuperlayerSprites;
	}
	
	public void setSuperlayerSprites(List<SuperlayerSprite> superlayerSprites){
		mySuperlayerSprites = superlayerSprites;
	}
	
	protected abstract SuperlayerSequence getNewInstance();
	
	public SuperlayerSequence clone(){
		SuperlayerSequence ret = getNewInstance();
		List<SuperlayerSprite> newSuperlayerSprites = new ArrayList<SuperlayerSprite>();
		mySuperlayerSprites.forEach(dialog->{
			newSuperlayerSprites.add(dialog.clone());
		});
		ret.setSuperlayerSprites(newSuperlayerSprites);
		ret.setName(new String(myName));
		return ret;
	}
	
	
}
