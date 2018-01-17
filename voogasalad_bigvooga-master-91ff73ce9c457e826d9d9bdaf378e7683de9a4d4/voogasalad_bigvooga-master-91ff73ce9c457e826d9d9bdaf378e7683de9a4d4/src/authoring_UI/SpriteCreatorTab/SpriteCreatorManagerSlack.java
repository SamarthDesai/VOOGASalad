package authoring_UI.SpriteCreatorTab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import engine.utilities.data.GameDataHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Main for sprite creator. AuthoringController creates an instance of this
 * class. Creates instances of DraggableGrid and SpriteCreatorSpritePanel
 * 
 * @author taekwhunchung
 *
 */
public class SpriteCreatorManagerSlack extends MapManager {

	private SpriteObjectGridManager SpriteCreatorGridBE;

	public SpriteCreatorManagerSlack(AuthoringEnvironmentManager AEM, Scene currentScene, String type) {
		super(AEM, currentScene, type);

	}

	@Override
	protected DraggableGrid makeDraggableGrid() {
		DraggableGrid ret = new DraggableGrid(GDH);
		if (myType.equals("SpriteObject")) {
			SpriteCreatorGridBE = new SpriteCreatorGridManager(myAEM,
					(Image im, String filePath) -> new SpriteObject(filePath, GDH));
		} else if (myType.equals("InventoryObject")) {
			SpriteCreatorGridBE = new SpriteCreatorGridManager(myAEM,
					(Image im, String filePath) -> new InventoryObject(filePath, GDH));
		} else {
			SpriteCreatorGridBE = new SpriteCreatorGridManager(myAEM,
					(Image im, String filePath) -> new SpriteObject(filePath, GDH));
		}
		List<SpriteObjectGridManager> grids = new ArrayList<SpriteObjectGridManager>();
		grids.add(SpriteCreatorGridBE);
		ret.setAllGrids(grids);
		return ret;
	}

	@Override
	protected void setManagerName() {
		MANAGER_NAME = myType.equals("InventoryObject") ? "InventoryCreator" : "SpriteCreator";
	}

	@Override
	protected void setTabTag() {
		TAB_TAG = myType.equals("InventoryObject") ? "InventoryCreator" : "SpriteCreator";
	}

	@Override
	protected List<DraggableGrid> getListOfDraggableGrids() {
		return new ArrayList<DraggableGrid>();
	}

	@Override
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler) {
		return new SpriteCreatorSpritePanels(mySpriteGridHandler, myAEM, myType);
	}

}