package authoring.GridManagers;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.PanelLayer;
import authoring_UI.Map.TerrainLayer;
import engine.utilities.data.GameDataHandler;
import javafx.scene.paint.Color;

public class PanelObjectGridManager extends SpriteObjectGridManager{

	public PanelObjectGridManager(int rows, int columns, SpriteGridHandler SGH, GameDataHandler GDH) {
		super(rows, columns, SGH, GDH);
		myLayerNum = 3;
	}
	
	public PanelObjectGridManager(int rows, int columns) {
		super(rows, columns);
		myLayerNum = 3;
	}
	
	public PanelObjectGridManager(int rows, int columns, GameDataHandler GDH) {
		super(rows, columns, GDH);
		myLayerNum = 3;
	}

	public PanelObjectGridManager(int myNumRows, int myNumCols, Color myColor) {
		super(myNumRows, myNumCols,  myColor);
		myLayerNum = 3;
	}


	@Override
	public void createMapLayer() {
		if (hasStoredSprites()){
			loadedFromData = true;
			myMapLayer = new PanelLayer(defaultRows, defaultColumns,mySpriteGridHandler,getStoredSpriteList());
		} else{
		myMapLayer = new PanelLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		}
		}
		
	
}
