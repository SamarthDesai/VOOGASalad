package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * Class that displays a list of inventories.
 * 
 * @author DavidTran
 *
 */
public class InventoryListView extends ListView<String> {

	private static double HEIGHT = 20;
	private List<String> iList = new ArrayList<>();
	private int inventoryCount = 0;

	public InventoryListView(List<Inventory> list) {

		this.setHeight(HEIGHT);

		for (Inventory i : list) {
			iList.add(i.getName());
			inventoryCount++;
		}
		ObservableList<String> items = FXCollections.observableArrayList(iList);

		this.setItems(items);

	}

	private String createListCellText(Inventory i) {
		TextField styled = new TextField(i.getName());
		styled.setFont(Font.font(i.getFontType()));
		styled.setStyle(i.getFontColor().toString());
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return inventoryCount + ":" + separator + " " + styled;
	}
}
