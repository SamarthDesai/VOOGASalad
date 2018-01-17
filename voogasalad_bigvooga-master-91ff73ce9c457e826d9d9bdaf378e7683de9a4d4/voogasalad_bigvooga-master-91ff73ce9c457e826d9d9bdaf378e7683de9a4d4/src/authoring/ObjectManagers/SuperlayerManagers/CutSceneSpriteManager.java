package authoring.ObjectManagers.SuperlayerManagers;

import engine.utilities.data.GameDataHandler;

public class CutSceneSpriteManager extends SuperlayerManager {

	public CutSceneSpriteManager(GameDataHandler GDH) {
		super(GDH);
	}

	protected void setFolderToLoad(){
		setFolderToLoad(myGDH.getCutSceneSpriteDirectoryPath());
	}

}
