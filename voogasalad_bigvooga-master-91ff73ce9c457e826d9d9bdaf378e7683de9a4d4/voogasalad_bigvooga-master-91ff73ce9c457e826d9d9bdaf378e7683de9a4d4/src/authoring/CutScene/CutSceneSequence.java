package authoring.CutScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoring.DialogSprite.SuperlayerSprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CutSceneSequence extends SuperlayerSequence{
	
	public CutSceneSequence(){
		
	}
	
	CutSceneSequence(String sequenceName, List<Pane> panes, String paneURL){
	super(sequenceName, panes, paneURL);
	}

	@Override
	protected SuperlayerSequence getNewInstance() {
		return new CutSceneSequence();
	}
	
	

	

}
