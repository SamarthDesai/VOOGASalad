package authoring.ObjectManagers.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetUserDefined extends SpriteSet{

	public SpriteSetUserDefined(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
		setFolderToLoad(myGDH.getCustomSpriteDirectoryPath());
		
	}
}