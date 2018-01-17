package authoring.ObjectManagers.SuperlayerManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.Thumbnail;
import authoring.CutScene.SuperlayerSequence;
import authoring.DialogSprite.AuthoringDialogSequence;
import authoring.DialogSprite.DialogThumbnail;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.SpriteThumbnail;
import engine.utilities.data.GameDataHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DialogSpriteManager extends SuperlayerManager {

	public DialogSpriteManager(GameDataHandler GDH) {
		super(GDH);
	}

	
	protected void setFolderToLoad(){
		setFolderToLoad(myGDH.getDialogSpriteDirectoryPath());
	}


}
