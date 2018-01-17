package authoring.SpritePanels;

import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DisplayPanelForTemplateSprites extends DisplayPanel{
	
	GameElementSelector elSelector;
	ObjectProperty<Boolean> elementSelectorSpriteActive;
	Consumer<Boolean> OnElementSpriteActiveConsumer;
	

	DisplayPanelForTemplateSprites(AuthoringEnvironmentManager AEM){
		super(AEM);
		elementSelectorSpriteActive = new SimpleObjectProperty<Boolean>();
		elementSelectorSpriteActive.set(false);
	}
	
	private void setElementSelectorSprite(boolean newStatus){
		getOnElementSpriteActiveConsumer().accept(newStatus);
		elementSelectorSpriteActive.set(newStatus);
	}
	
	@Override
	protected void checkMultipleCellsActive() {
		this.setMultipleCellsActive(false);
	}
	
	@Override
	protected void applyToMultipleAtOnce(){
		// Do nothing;
	}
	
	@Override
	protected AbstractSpriteObject getActiveCell() throws Exception {
		return activeSprite;
	}
	
	@Override
	public AbstractSpriteObject setActiveSprite(AbstractSpriteObject ASO) {
		AbstractSpriteObject ret = activeSprite;
		if (ASO!=null){
			if (activeSprite==null || !activeSprite.equals(ASO)) {
				activeSprite = ASO;
				this.setElementSelectorSprite(true);
			} else {
				activeSprite = null;
				this.setElementSelectorSprite(false);
			}
			
		} else {
			this.setElementSelectorSprite(false);		
			}
		if (ret!=null){
			ret.clearPossibleParameters();
		}
		return ret;
	}
	
	public void setOnElementSpriteActive(Consumer<Boolean> cons){
		OnElementSpriteActiveConsumer = cons;
	}
	
	public Consumer<Boolean> getOnElementSpriteActiveConsumer(){
		return OnElementSpriteActiveConsumer;
	}
	
	
	@Override
	protected boolean multipleActive(){
		return false;
	}
	
}
