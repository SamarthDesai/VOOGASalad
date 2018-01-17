package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for extracting all inventory information and converting into inventory objects.
 * 
 * @author DavidTran, Dara Buggay
 *
 */
public class InventoryExtractor {

	private List<Inventory> inventoryList;

	public InventoryExtractor() {
		inventoryList = new ArrayList<>();

	}

	protected void extract(List<InventoryEditor> editorList) {
		inventoryList = new ArrayList<>();

	}

	public List<Inventory> getInventoryList() {
		return inventoryList;
	}
}
