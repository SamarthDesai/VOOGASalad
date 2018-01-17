package authoring;

import authoring.Sprite.SpriteObject;
import authoring_actionconditions.ActionTab;

public class ControllerActionConditionTabsSpriteObject implements ControllerActionConditionTabI{
	
	private SpriteObject spriteObject;
	private ActionTab conditionTab;
	private ActionTab actionTab;

	public ControllerActionConditionTabsSpriteObject(SpriteObject spriteObject,ActionTab conditionTab,ActionTab actionTab) {
		this.spriteObject = spriteObject;
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
	}

	@Override
	public void loadActionConditions() {
		
	}

	@Override
	public void setActionConditions() {
		
	}
	
}
