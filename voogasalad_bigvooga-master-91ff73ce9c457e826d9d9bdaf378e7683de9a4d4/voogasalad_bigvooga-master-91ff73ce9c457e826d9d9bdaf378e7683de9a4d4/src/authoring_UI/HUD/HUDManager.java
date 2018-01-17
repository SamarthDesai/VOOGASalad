package authoring_UI.HUD;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import engine.utilities.data.GameDataHandler;
import javafx.scene.Scene;

public class HUDManager extends MapManager{

	
	private SpriteObjectGridManager HUDGridBE;

	public HUDManager(AuthoringEnvironmentManager AEM, Scene currentScene) {
		super(AEM, currentScene);
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){

		System.out.println("DG in HUDMANAGER");
		DraggableGrid ret = new DraggableGrid(GDH);
		HUDGridBE = new HUDGridManager();
		BackgroundGridManager BackgroundGrid = new BackgroundGridManager(HUDGridBE.getDefaultRows(), HUDGridBE.getDefaultCols(), GDH);
		List<SpriteObjectGridManager> grids = new ArrayList<SpriteObjectGridManager>();
		grids.add(BackgroundGrid);
		grids.add(HUDGridBE);
		ret.setAllGrids(grids);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGER_NAME = "HUDManager";
	}
	
	@Override 
	protected void setTabTag(){
		TAB_TAG="HUD";
	}
	
	@Override
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return new ArrayList<DraggableGrid>();
	}
	
	@Override
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new SpritePanels(mySpriteGridHandler, myAEM);
	}

	
}


	
