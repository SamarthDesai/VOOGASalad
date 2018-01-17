package authoring_UI.Inventory;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

/**
 * Class that holds all inventory tabs
 * 
 * @author DavidTran, Dara Buggay
 *
 */
public class InventoryTabPane extends TabPane {

	private static final String DEFAULT = "DefaultInventories";
	private static final String USER = "UserCreatedInventories";
	private DefaultInventoryTab defaultTab;
	private UserInventoryTab userTab;

	public InventoryTabPane() {
		
		defaultTab = new DefaultInventoryTab(DEFAULT);
		userTab = new UserInventoryTab(USER);
		this.getTabs().addAll(defaultTab, userTab);
	}
	
	public void addDefaultInventoryButton(int number, Button btn) {
		defaultTab.addDisplayable(number, btn);
	}
	
	public void addUserInventoryButton(int number, Button btn) {
		userTab.addDisplayable(number, btn);
	}

	public void removeUserInventoryButton(int id) {
		userTab.deleteDisplayable(id);
	}
	
	public int getButtonIndex(Button btn) {
		return userTab.getButtonIndex(btn);
	}

}
