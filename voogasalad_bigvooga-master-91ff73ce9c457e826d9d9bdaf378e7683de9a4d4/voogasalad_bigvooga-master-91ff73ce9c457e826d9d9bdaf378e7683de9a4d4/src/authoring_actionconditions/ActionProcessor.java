package authoring_actionconditions;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

public class ActionProcessor {

	private TreeView<HBox> treeView;
	private TreeItem<HBox> actionCategory, action;
	private ChoiceBox<String>[] arr;

	public ActionProcessor(TreeView<HBox> treeView, TreeItem<HBox> actionCategory, TreeItem<HBox> actionName) {
		this.treeView = treeView;
		this.actionCategory = actionCategory;
		this.action = actionName;

		if (actionCategory != null) {
			for (int i = 0; i < action.getChildren().size(); i++) {
				TreeItem<HBox> item = action.getChildren().get(i);

				for (int j = 0; j < item.getChildren().size(); j++) {
					HBox operationHBox = item.getChildren().get(j).getValue();
					
					ChoiceBox<String> operation = (ChoiceBox<String>) operationHBox.getChildren().get(1);
					
				}

				;
			}
		}

		// for (int i=0; i<arr.length; i++) {
		//
		// ;
		// }
		//
	}

}
