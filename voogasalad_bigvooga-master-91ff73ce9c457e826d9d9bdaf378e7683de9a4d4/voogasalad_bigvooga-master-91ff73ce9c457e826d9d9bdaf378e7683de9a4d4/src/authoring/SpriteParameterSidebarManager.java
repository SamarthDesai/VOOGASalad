package authoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.Parameters.SpriteParameter;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring_UI.DraggableGrid;

public class SpriteParameterSidebarManager {
	Map<String, List<SpriteParameter>> everyStateParameter = new HashMap<String, List<SpriteParameter>>();
	Map<String, String> newNameOldName = new HashMap<String, String>();
	boolean firstTimeThrough = true;
	AbstractSpriteObject firstSprite;
	DraggableGrid myDG;
	private boolean multipleCellsActive = false;

	public SpriteParameterSidebarManager(DraggableGrid DG) {
		myDG = DG;
	}
	
	public boolean multipleActive(){
		return multipleCellsActive;
	}

	public AbstractSpriteObject getActiveSprite() throws Exception {
		List<AbstractSpriteObject> sprites = myDG.getActiveGrid().getActiveSpriteObjects();
		System.out.println("SPSM getting active sprites, size: "+sprites.size());
		if (sprites.size()>0){
		System.out.println("First sprite: "+sprites.get(0));
		}
		checkActiveCellsMatch(sprites);
		if (firstSprite==null){
			throw new Exception("No active cells");
		}
		;
		return firstSprite;
	}

	private void checkActiveCellsMatch(List<AbstractSpriteObject> SO_List) throws Exception {
		multipleCellsActive = (SO_List.size()>1);
		if (SO_List.size() > 0) {
			firstTimeThrough = true;
			for (AbstractSpriteObject SO : SO_List) {
				if (firstTimeThrough) {
					initializeMaps(SO);
					firstTimeThrough = false;
				} else {
					boolean matches = SO.isSame(firstSprite);
					if (!matches) {
						throw new Exception("Sprites are not identical");
					}
				}
			}
		} else {
			setNoCellsActive();
		}
	}

	private void initializeMaps(AbstractSpriteObject SO) {
		firstSprite = SO;
		everyStateParameter = SO.getParameters();
		newNameOldName = new HashMap<String, String>();
	}
	
	public void setNoCellsActive() {
		firstTimeThrough = true;
		firstSprite = null;
		everyStateParameter = null;
		newNameOldName = null;
	}
	
	public List<AbstractSpriteObject> getAllSpritesFromActiveGrid(){
		return this.myDG.getActiveGrid().getEntireListOfSpriteObjects();
	}

	public void apply() {
		myDG.getActiveGrid().matchActiveCellsToSprite(firstSprite);
	}
}