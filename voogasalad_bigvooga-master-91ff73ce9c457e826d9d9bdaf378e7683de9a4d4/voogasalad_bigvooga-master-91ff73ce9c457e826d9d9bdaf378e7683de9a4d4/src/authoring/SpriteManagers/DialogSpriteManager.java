package authoring.SpriteManagers;

import java.io.File;
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

public class DialogSpriteManager {

	protected List<SuperlayerSequence> dialogSequences;
	protected GameDataHandler myGDH;
	protected String folderToLoad;
	protected boolean loaded = false;
	protected List<SpriteObject> toSave;

//	private final String FOLDER = "Dialogue";

	public DialogSpriteManager(GameDataHandler GDH) {
		myGDH = GDH;
		setFolderToLoad();
		loadSprites();
		toSave = new ArrayList<SpriteObject>();
	}

	public List<Pane> getAllSpritesAsThumbnails() {
		List<SuperlayerSequence> ASOs = getAllDialogSequences();
		List<Pane> ret = new ArrayList<Pane>();
		ASOs.forEach(sprite -> {
//			Image im = new Image(sprite.getDialogSprites().get(0).getImageFileURL());
			ret.add(new DialogThumbnail(sprite.getImage(), sprite.getName()));
		});
		return ret;
	}

	public List<SuperlayerSequence> getAllDialogSequences() {
		if (!loaded) {
			this.loadSprites();
		}
		List<SuperlayerSequence> ret = new ArrayList<SuperlayerSequence>();
		ret.addAll(dialogSequences);
		return ret;

	}

	protected String getFolderToLoad() {
		// ;
		return folderToLoad;
	}

	protected void setFolderToLoad(String path) {
		folderToLoad = path;
	}
	
	private void setFolderToLoad() {
		setFolderToLoad(myGDH.getDialogSpriteDirectoryPath());
	}

	protected void loadSprites() {
		if (dialogSequences == null) {
			dialogSequences = new ArrayList<SuperlayerSequence>();
		}
		loaded = true;
		if (!getFolderToLoad().equals("")) {
			dialogSequences = myGDH.loadSuperlayerSequencesFromDirectory(getFolderToLoad());
		}
	}

	public void setBooleanLoaded(Boolean b) {
		loaded = b;
	}

	public void addNewDialogSequence(AuthoringDialogSequence DS) {

		dialogSequences.add(DS);
		saveDialogSequence(DS);
	}

	protected void saveDialogSequence(AuthoringDialogSequence DS) {
		String folderToSaveTo = getFolderToLoad() + "/" + DS.getName();
		myGDH.saveSuperlayerSequence(DS, folderToSaveTo);
	}

}
