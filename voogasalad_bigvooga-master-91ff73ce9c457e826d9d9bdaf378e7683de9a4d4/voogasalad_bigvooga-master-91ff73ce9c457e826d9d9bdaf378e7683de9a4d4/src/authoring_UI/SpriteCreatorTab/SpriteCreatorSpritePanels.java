package authoring_UI.SpriteCreatorTab;

import authoring.SpritePanels.*;
import authoring_UI.*;
import authoring.*;

/**
 * SpriteCreatorSpritePanels extends SpritePanels which extends VBox. Contains
 * DisplayPanel and SpriteCreatorElementSelector
 * 
 * @author taekwhunchung
 *
 */

public class SpriteCreatorSpritePanels extends SpritePanels {

	SpriteCreatorSpritePanels() {
		super();

	}

	public SpriteCreatorSpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM, String type) {
		super(mySGH, myAEM, type);

	}

	@Override
	public void makeLayerDisplayPanel(AuthoringEnvironmentManager myAEM) {
		displayPanel = new DisplayPanel(SPSM, myAEM);
	}

	@Override
	public void makeElementSelector(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM) {
		gameElementSelector = new SpriteCreatorElementSelector(mySGH, myAEM, myType);
	}
}
