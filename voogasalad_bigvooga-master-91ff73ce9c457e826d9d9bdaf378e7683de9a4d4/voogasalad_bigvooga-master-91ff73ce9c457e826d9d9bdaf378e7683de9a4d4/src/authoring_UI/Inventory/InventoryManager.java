package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring_UI.MapManager;
import authoring_UI.displayable.DisplayableManager;
import engine.utilities.data.GameDataHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that represents the pane containing all inventory authoring components
 * 
 * @author DavidTran, Dara Buggay
 *
 */
public class InventoryManager extends DisplayableManager {

	private static final double NODE_SPACING = 20;
	private static final double BUTTON_WIDTH = 300;
	private static final double BUTTON_HEIGHT = 75;
	private static final String ADD_BUTTON_PROMPT = "New";
	private static final String SAVE_BUTTON_PROMPT = "Save";

	private HBox hb;
	private InventoryExtractor iExtractor;
	private InventoryListView listView;

	private Tab mapInventoriesTab;
	private GameDataHandler GDH;
	private AuthoringEnvironmentManager AEM;
	private InventoryEditor currentEditor;

	public InventoryManager(AuthoringEnvironmentManager currentAEM) {
		AEM = currentAEM;
		GDH = AEM.getGameDataHandler();
		iExtractor = new InventoryExtractor();
		hb = new HBox(NODE_SPACING);
		hb.setLayoutX(35);
		hb.setPadding(new Insets(20, 0 ,0 ,0));
		hb.getChildren().addAll(createSeparator(), createButtonPanel());

		// test
		// addDefaultinventoryButton();
		// addUserinventoryButton("blah", -1);

	}

	@Override
	protected Separator createSeparator() {
		return super.createSeparator();
	}

	@Override
	protected Separator createShortSeparator(int height) {
		return super.createShortSeparator(height);
	}


	public HBox getPane() {
		return hb;
	}

	@Override
	protected void save() {
		if (!currentEditor.getBackgroundIsColor()) {
				//InventorySequence inventorySequence = new InventorySequence(currentEditor.getName(), currentEditor.getInventorySequence(), currentEditor.getBackgroundImage());
				//AEM.getDialogSpriteController().addNewDialogSequence(inventorySequence);
			}
			else if (currentEditor.getBackgroundIsColor()) {
				//DialogSequence dialogSequence = new DialogSequence(currentEditor.getName(), currentEditor.getInventorySequence(), currentEditor.getBackgroundColor());
				//AEM.getDialogSpriteController().addNewDialogSequence(dialogSequence);
			}


		updateListView();
	}

	protected void newEditor() {
		currentEditor = new InventoryEditor(e -> save(), GDH);
		hb.getChildren().addAll(createShortSeparator(400), currentEditor.getParent());

	}

	protected VBox createButtonPanel() {
		VBox vb = new VBox(NODE_SPACING);
		vb.getChildren().addAll(createButton(ADD_BUTTON_PROMPT, e -> newEditor()),
				createButton(SAVE_BUTTON_PROMPT, e -> save()));
		return vb;
	}

}
