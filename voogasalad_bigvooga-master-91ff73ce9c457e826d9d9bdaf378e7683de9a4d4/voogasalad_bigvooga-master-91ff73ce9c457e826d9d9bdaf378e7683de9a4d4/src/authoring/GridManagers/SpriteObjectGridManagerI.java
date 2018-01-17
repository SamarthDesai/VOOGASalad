package authoring.GridManagers;

import java.util.ArrayList;
import java.util.List;

import authoring.Sprite.SpriteObject;
import authoring.Sprite.Parameters.SpriteParameterI;
import javafx.scene.image.ImageView;

public interface SpriteObjectGridManagerI {

	ImageView [][] getGrid();
	void populateCell(SpriteObject spriteObject, ArrayList<Integer []> row_col);
	public ArrayList<SpriteParameterI> getSpriteParameters(ArrayList<Integer []> row_col);
	List<SpriteObject> getActiveSpriteObjects();
	void clearCells(ArrayList<Integer[]> cellsToClear);
	void resetActiveCells();
	void removeActiveCells(ArrayList<Integer[]> makeInactive);
	void switchCellActiveStatus(ArrayList<Integer[]> makeActive);
	void matchActiveCellsToSprite(SpriteObject firstSprite);
	List<SpriteObject> getEntireListOfSpriteObjects();
//	void switchCellActiveStatus(SpriteObjectI SOI);
	boolean switchCellActiveStatus(Integer[] makeActive);
	void populateCell(SpriteObject spriteObject, Integer[] row_col);
	int getLayerNum();
	
}
