package authoring.ObjectManagers.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetImported extends SpriteSet {
	
	public SpriteSetImported(GameDataHandler GDH){
		super(GDH);
	}

	@Override
	public void setFolderToLoad() {
		String importedSpritesPath = myGDH.getImportedSpritesPath();
		System.out.println("HERE'S THE PATH WE ARE IMPORTING SPRITES FROM: " + importedSpritesPath);
		setFolderToLoad(importedSpritesPath);
	}
	
	public void changeFolderPath(){
		this.categoryToSprites.clear();
		this.setFolderToLoad();
		this.loadSprites();
	}
}