package authoring_UI.Menu;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.SpritePanels.MenuSpritePanels;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import engine.utilities.data.GameDataHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuManager extends MapManager{
	
	private SpriteObjectGridManager MenuGridBE;

	public MenuManager(AuthoringEnvironmentManager AEM, Scene currentScene) {
		super(AEM, currentScene);
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){
		DraggableGrid ret = new DraggableGrid(GDH);
		MenuGridBE = new MenuGridManager();
		BackgroundGridManager BackgroundGrid = new BackgroundGridManager(MenuGridBE.getDefaultRows(), MenuGridBE.getDefaultCols(), GDH);
		List<SpriteObjectGridManager> grids = new ArrayList<SpriteObjectGridManager>();
		grids.add(BackgroundGrid);
		grids.add(MenuGridBE);
		ret.setAllGrids(grids);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGER_NAME = "MenuManager";
	}
	
	@Override 
	protected void setTabTag(){
		TAB_TAG="MenuCreator";
	}
	
	@Override
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return new ArrayList<DraggableGrid>();
	}
	
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new MenuSpritePanels(mySpriteGridHandler, myAEM);
	}
	
	
	

}