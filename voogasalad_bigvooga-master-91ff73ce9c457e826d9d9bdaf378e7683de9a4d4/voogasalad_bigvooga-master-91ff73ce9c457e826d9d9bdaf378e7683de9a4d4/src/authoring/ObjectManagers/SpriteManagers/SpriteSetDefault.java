package authoring.ObjectManagers.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetDefault extends SpriteSet{
	
	public SpriteSetDefault(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
		setFolderToLoad(myGDH.getDefaultSpriteDirectoryPath());
	}
	
	public void changeFolderPath(String newFolder){
		this.categoryToSprites.clear();
		this.setFolderToLoad();
		this.loadSprites();
	}
}