package authoring.DialogSprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoring.CutScene.SuperlayerSequence;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class AuthoringDialogSequence extends SuperlayerSequence{
	
	public AuthoringDialogSequence(){
		
	}
	
	public AuthoringDialogSequence(String sequenceName, List<Pane> panes, String paneURL){
	super(sequenceName, panes, paneURL);
	}

	@Override
	protected SuperlayerSequence getNewInstance() {
		return new AuthoringDialogSequence();
	}

}
