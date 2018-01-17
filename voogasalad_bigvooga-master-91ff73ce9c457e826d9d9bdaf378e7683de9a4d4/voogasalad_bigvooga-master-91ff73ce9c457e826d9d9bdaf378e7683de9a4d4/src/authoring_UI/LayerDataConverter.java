package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.PanelObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManagerForSprites;
import authoring.GridManagers.TerrainObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import engine.utilities.data.GameDataHandler;

public class LayerDataConverter {

	private int myNumRows;
	private int myNumCols;
	private int layerNum;
	private String myName;
	private List<SpriteDataConverter> mySprites;
	private GameDataHandler GDH;
	
	public String getName(){
		return myName;
	}
	
	public LayerDataConverter getToSerialize(){
		return this;
	}
	
	public LayerDataConverter(SpriteObjectGridManager SOGM) {
		convertLayer(SOGM);
	}
	
	public void setGameDataHandler(GameDataHandler GDH){
		this.GDH = GDH;
	}
	
	public void convertLayer(SpriteObjectGridManager SOGM){
//		myColor = SOGM.getColor();
		mySprites = new ArrayList<SpriteDataConverter>();
		myName = SOGM.getName();
		myNumRows = SOGM.getNumRows();
		myNumCols = SOGM.getNumCols();
		layerNum = SOGM.getLayerNum();
		SOGM.getEntireListOfSpriteObjects().forEach(sprite->{
			mySprites.add(new SpriteDataConverter(sprite));
		});
		GDH = null;
	}
	
	public SpriteObjectGridManager createLayer() {
		SpriteObjectGridManager newLayer = null;
		if (myName.equals("Background")) {
			newLayer = new BackgroundGridManager(myNumRows, myNumCols, GDH);
		}
	
		else if (myName.equals("Terrain")) {
			newLayer = new TerrainObjectGridManager(myNumRows, myNumCols, GDH);
		}
		else if (myName.equals("Main View")) {
			newLayer = new SpriteObjectGridManagerForSprites(myNumRows, myNumCols, GDH);
		}
		else if (myName.equals("Panels")){
			newLayer = new PanelObjectGridManager(myNumRows, myNumCols, GDH);
		}
		List<AbstractSpriteObject> toStore = new ArrayList<AbstractSpriteObject>();
		mySprites.forEach(SDC->{
			SDC.setGameDataHandler(GDH);
			toStore.add(SDC.createSprite());
		});
		newLayer.storeSpriteObjectsToAdd(toStore);
		return newLayer;
	}
}