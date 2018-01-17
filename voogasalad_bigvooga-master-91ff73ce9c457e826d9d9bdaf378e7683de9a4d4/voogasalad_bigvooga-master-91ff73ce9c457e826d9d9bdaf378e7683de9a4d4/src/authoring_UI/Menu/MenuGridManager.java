package authoring_UI.Menu;

import java.util.ArrayList;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring_UI.SpriteGridHandler;
import engine.utilities.data.GameDataHandler;
import javafx.scene.image.ImageView;

public class MenuGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 10;
	private static int COLUMNS = 6;
	
	
	public MenuGridManager(SpriteGridHandler SGH, GameDataHandler GDH){
		super(ROWS, COLUMNS, SGH, GDH);
	}
	
	public MenuGridManager(){
		super(ROWS, COLUMNS);
	}

	protected MenuGridManager(int rows, int columns, SpriteGridHandler SGH, GameDataHandler GDH) {
		super(rows, columns, SGH, GDH);
	}
	
	@Override
	public void createMapLayer() {
		myMapLayer = new MenuLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
		
	}
	
	@Override
	public int getLayerNum() {
		return this.myLayerNum;
	}

}
