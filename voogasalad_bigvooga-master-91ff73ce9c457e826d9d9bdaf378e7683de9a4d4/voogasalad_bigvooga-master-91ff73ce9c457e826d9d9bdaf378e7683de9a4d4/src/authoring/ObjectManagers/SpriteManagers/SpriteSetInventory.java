package authoring.ObjectManagers.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetInventory extends SpriteSet {

	public SpriteSetInventory(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
		setFolderToLoad(myGDH.getInventorySpriteDirectoryPath());

	}

//	@Override
//	protected void makeSpritePanel(SpriteGridHandler SGH) {
//		mySSP = new SpriteSelectPanel("INVENTORYSPRITES", SGH);
//		mySSP.setupDefaultSprites(getAllSprites());
//	}

}
