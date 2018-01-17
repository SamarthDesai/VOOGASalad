package authoring.GridManagers;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.TerrainLayer;
import engine.utilities.data.GameDataHandler;
import javafx.scene.paint.Color;

public class TerrainObjectGridManager extends SpriteObjectGridManager{

	public TerrainObjectGridManager(int rows, int cols, SpriteGridHandler SGH, GameDataHandler GDH){
		super(rows, cols, SGH, GDH);
		myLayerNum = 1;
	}
	
	public TerrainObjectGridManager(int rows, int cols, GameDataHandler GDH) {
		super(rows, cols, GDH);
		myLayerNum = 1;
	}
	
	public TerrainObjectGridManager(int rows, int cols) {
		super(rows, cols);
		myLayerNum = 1;
	}
	
	public TerrainObjectGridManager(int myNumRows, int myNumCols, Color myColor) {
		super(myNumRows, myNumCols,myColor);
		myLayerNum = 1;
	}

	
	@Override
	public void createMapLayer() {
			if (hasStoredSprites()){
				loadedFromData = true;
				myMapLayer = new TerrainLayer(defaultRows, defaultColumns,mySpriteGridHandler,getStoredSpriteList());
			} else{
			myMapLayer = new TerrainLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
			}
			}
	
	@Override
	public int getLayerNum() {
		return myLayerNum;
		//return myMapLayer.getLayerNumber();
	}

}