package authoring_actionconditions;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class that opens a window and allows the user to create actions via a recursive tree-view. 
 * 
 * @author DavidTran
 *
 */
public class BuildActionView {
	private static final double WIDTH = ActionConditionRow.ROW_WIDTH;
	private static final double HEIGHT = ActionRow.ROW_EXPANDED_HEIGHT;
	private static final String BUILDVIEW_CSS = "BuildView.css";

	private Stage stage;
	private Scene scene;
	private Group root;
	ActionConditionVBox<?> ACVBox;
	private ActionRow ACRow;

	public BuildActionView(ActionConditionVBox<?> ACVBox, ActionRow ACRow) {
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();

		this.ACRow = ACRow;
		this.ACVBox = ACVBox;

		stage.setOnCloseRequest(event -> transportActionRow(event));

		root.getChildren().add(this.ACRow);

		// scene.getStylesheets().add(BuildActionView.class.getResource(BUILDVIEW_CSS).toExternalForm());
	}

	private void transportActionRow(WindowEvent event) {

		try {
			ACRow.getTreeView().getAction();

			;

		} catch (Exception e) {
			ConditionTreeView.showError(e.getMessage());

			// event.consume();
		}

		ACRow.reduceTreeView();

		if (ACVBox.getChildren().size() >= ACRow.getRowID())
			ACVBox.getChildren().remove(ACRow.getRowID() - 1);

		ACVBox.getChildren().add(ACRow.getRowID() - 1, ACRow);

		stage.close();
	}
}
