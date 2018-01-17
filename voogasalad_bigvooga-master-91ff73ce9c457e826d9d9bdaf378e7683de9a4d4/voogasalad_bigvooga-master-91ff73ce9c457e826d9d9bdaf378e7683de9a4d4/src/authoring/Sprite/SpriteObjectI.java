package authoring.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import authoring.Sprite.Parameters.SpriteParameterI;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public interface SpriteObjectI {

	HashMap<String, ArrayList<SpriteParameterI>> getParameters();
	void addParameter(SpriteParameterI SP);
	void applyParameterUpdate(HashMap<String, ArrayList<SpriteParameterI>> newParams);
	boolean isSame(SpriteObject other);
	SpriteObjectI newCopy();
	ImageView getImageView();
	void setImageURL(String fileLocation);
	Integer[] getPositionOnGrid();
	void setPositionOnGrid(Integer[] pos);
	String getName();
	void setName(String name);
	void updateCategoryName(String prev, String next);
	public void setAllActions(ObservableList<Integer> allActions);
	public void setCondidtionRows(HashMap<List<String>,List<Integer>> conditionRows);
	public void setActionRows(List<List<String>> actionRows);
	public ObservableList<Integer> getAllActions();
	public HashMap<List<String>,List<Integer>> getConditionRows();
	public List<List<String>> getActionRows();
	
	
}

