package authoring_UI.Inventory;

import javafx.scene.control.ListCell;

public class InventoryListCell extends ListCell<String> {

	private Inventory inventory;
	
	public InventoryListCell(Inventory d) {
		this.setText("a");
		this.inventory = d;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
}
