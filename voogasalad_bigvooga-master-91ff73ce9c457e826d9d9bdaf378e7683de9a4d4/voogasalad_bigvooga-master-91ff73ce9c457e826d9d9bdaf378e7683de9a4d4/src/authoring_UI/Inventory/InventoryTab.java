package authoring_UI.Inventory;

import authoring_UI.ViewSideBar;
import authoring_UI.displayable.DisplayableTab;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that represents a tab listing inventories to edit
 * 
 * @author DavidTran, Dara Buggay
 *
 */
public class InventoryTab extends DisplayableTab {

	private static final double INVENTORY_SPACING = 25;
	private static final double PADDING = 25;

	private VBox inventoryLister;
	private ScrollPane sp;

	public InventoryTab(String name) {
		super(name);
		this.setContent(sp);
		inventoryLister = new VBox();

		this.textProperty().bind(DisplayLanguage.createStringBinding(name));

		inventoryLister = makeVBox((WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH) / 2, WelcomeScreen.HEIGHT,
				INVENTORY_SPACING);
		inventoryLister.setAlignment(Pos.TOP_CENTER);
		inventoryLister.setPadding(new Insets(PADDING));

		sp = new ScrollPane();
		sp.setContent(inventoryLister);
		sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		this.setContent(sp);
	}


	@Override
	protected VBox makeVBox(double width, double height, double spacing) {
		return super.makeVBox(width, height, spacing);
	}

	@Override
	protected void addDisplayable(int index, Button btn) {
		if (inventoryLister.getChildren().size() > index) {
			inventoryLister.getChildren().remove(index);
			inventoryLister.getChildren().add(index, btn);
		} else
			inventoryLister.getChildren().add(btn);
		// ;

	}

	@Override
	protected void deleteDisplayable(int index) {
		inventoryLister.getChildren().remove(index);

	}

	@Override
	protected int getButtonIndex(Button btn) {
		return inventoryLister.getChildren().indexOf(btn);
	}

}
