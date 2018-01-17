package authoring.ObjectManagers.SuperlayerManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.Thumbnail;
import authoring.CutScene.SuperlayerSequence;
import authoring.CutScene.SuperlayerThumbnail;
import authoring.DialogSprite.AuthoringDialogSequence;
import authoring.DialogSprite.DialogThumbnail;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.SpriteThumbnail;
import engine.utilities.data.GameDataHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class SuperlayerManager {

	protected List<SuperlayerSequence> superlayerSequences;
	protected GameDataHandler myGDH;
	protected String folderToLoad;
	protected boolean loaded = false;

	public SuperlayerManager(GameDataHandler GDH) {
		myGDH = GDH;
		setFolderToLoad();
		loadSprites();
	}

	public List<Pane> getAllSpritesAsThumbnails() {
		List<SuperlayerSequence> ASOs = getAllSuperlayerSequences();
		List<Pane> ret = new ArrayList<Pane>();
		ASOs.forEach(sprite -> {
			ret.add(new SuperlayerThumbnail(sprite));
		});
		return ret;
	}

	public List<SuperlayerSequence> getAllSuperlayerSequences() {
		if (!loaded) {
			this.loadSprites();
		}
		List<SuperlayerSequence> ret = new ArrayList<SuperlayerSequence>();
		ret.addAll(superlayerSequences);
		return ret;
	}

	protected String getFolderToLoad() {
		return folderToLoad;
	}

	protected void setFolderToLoad(String path) {
		folderToLoad = path;
	}
	
	protected abstract void setFolderToLoad();

	protected void loadSprites() {
		if (superlayerSequences == null) {
			superlayerSequences = new ArrayList<SuperlayerSequence>();
		}

		loaded = true;
		if (!getFolderToLoad().equals("")) {
			superlayerSequences = myGDH.loadSuperlayerSequencesFromDirectory(getFolderToLoad());
		}
	}

	public void setBooleanLoaded(Boolean b) {
		loaded = b;
	}

	public void addNewSuperlayerSequence(SuperlayerSequence SS) throws Exception {
		superlayerSequences.add(SS);
		saveSuperlayerSequence(SS);
	}

	protected void saveSuperlayerSequence(SuperlayerSequence SS) throws Exception {
		String folderToSaveTo = getFolderToLoad() + "/" + SS.getName();
		myGDH.saveSuperlayerSequence(SS, folderToSaveTo);
	}

}
