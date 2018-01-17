package authoring.DialogSprite;

import java.util.function.Supplier;

import authoring.CutScene.SuperlayerSequence;
import javafx.scene.image.ImageView;

public class SuperlayerAuthoringImage extends ImageView{

	private SuperlayerSequence mySLS;
	
	public SuperlayerAuthoringImage(SuperlayerSprite DS, Supplier<SuperlayerSequence> mySuperlayerSeq){
		super(DS.getImageFileURL());
		mySLS = mySuperlayerSeq.get();
	}
	
	public SuperlayerSequence getSuperlayerSequence(){
		return mySLS;
	}
	
	
}
