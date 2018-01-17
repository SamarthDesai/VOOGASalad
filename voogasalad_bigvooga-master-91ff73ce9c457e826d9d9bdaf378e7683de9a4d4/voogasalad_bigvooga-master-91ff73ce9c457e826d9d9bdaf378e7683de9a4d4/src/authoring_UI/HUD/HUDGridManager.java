package authoring_UI.HUD;

import java.util.List;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import engine.utilities.data.GameDataHandler;

public class HUDGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 2;
	private static int COLUMNS = 20;
	
	
	public HUDGridManager(SpriteGridHandler SGH, GameDataHandler GDH){
		super(ROWS, COLUMNS, SGH, GDH);
	}
	
	public HUDGridManager(){
		super(ROWS, COLUMNS);
	}


	protected HUDGridManager(int rows, int columns, SpriteGridHandler SGH, GameDataHandler GDH) {
		super(rows, columns, SGH, GDH);

	}
	
	@Override
	public void createMapLayer() {
		myMapLayer = new HUDLayer(getNumRows(), getNumCols(), mySpriteGridHandler);		
	}


}
