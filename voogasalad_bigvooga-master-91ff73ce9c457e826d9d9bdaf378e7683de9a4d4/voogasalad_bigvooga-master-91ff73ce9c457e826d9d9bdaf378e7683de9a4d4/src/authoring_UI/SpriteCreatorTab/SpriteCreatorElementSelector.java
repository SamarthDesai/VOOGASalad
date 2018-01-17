package authoring_UI.SpriteCreatorTab;

import authoring.AuthoringEnvironmentManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.SpritePanels.GameElementSelector;
import authoring_UI.SpriteGridHandler;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * This is where users can select from already existing sprites in sprite
 * creator. Extends GameElementSelector, which extends TabPane.
 * 
 * @author taekwhunchung
 *
 */

public class SpriteCreatorElementSelector extends GameElementSelector {

	protected SpriteCreatorElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM,
			String type) {
		super(spriteGridHandler, AEM, type);
	}

	@Override
	protected void createSpriteTabs() {
		if (myType.equals("InventoryObject")) {
			Tab inventoryTab = createSubTab(INVENTORY, myAEM.getInventoryController());
			Tab importedInventoryTab = createSubTab(IMPORTEDINVENTORY, myAEM.getImportedInventorySpriteController());
			this.getTabs().addAll(inventoryTab, importedInventoryTab);
			this.setSide(Side.TOP);
		} else {
			Tab defaultSpriteTab = createSubTab(DEFAULT, myAEM.getDefaultSpriteController());
			Tab userSpriteTab = createSubTab(USER, myAEM.getCustomSpriteController());
			Tab importedSpriteTab = createSubTab(IMPORTED, myAEM.getImportedSpriteController());

			this.getTabs().addAll(defaultSpriteTab, userSpriteTab, importedSpriteTab);
			this.setSide(Side.TOP);
		}
	}

	@Override
	protected void addSpriteGridHandlerFunctionality(AbstractSpriteObject ASO) {
		ASO.setOnMouseClicked(e -> {
			((SpriteCreatorGridManager) this.mySpriteGridHandler.getDraggableGrid().getActiveGrid())
					.setSpriteToCoverEntireGrid(ASO.newCopy());
		});
	}

}
