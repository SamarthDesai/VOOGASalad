package authoring_actionconditions;

import engine.Condition;
import engine.operations.OperationFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public class ConditionTreeItem extends TreeItem<HBox> {

	private OperationFactory operationFactory = new OperationFactory();
	private Runnable changeTreeViewSize;

	public ConditionTreeItem(Runnable changeSize) {
		changeTreeViewSize = changeSize;
		this.makeOperationTreeItem();
	}

	private TreeItem<HBox> makeOperationTreeItem() {
		
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Boolean Operation: "), makeOperationChoiceBox(this));
		this.setValue(hb);
		this.setExpanded(true);
		this.expandedProperty().addListener(e -> changeTreeViewSize.run());
		return this;

	}


	private Node makeOperationChoiceBox(ConditionTreeItem conditionTreeItem) {
		// TODO Auto-generated method stub
		return null;
	}

	public Condition extract() {
		// TODO Auto-generated method stub
		return null;
	}
}
