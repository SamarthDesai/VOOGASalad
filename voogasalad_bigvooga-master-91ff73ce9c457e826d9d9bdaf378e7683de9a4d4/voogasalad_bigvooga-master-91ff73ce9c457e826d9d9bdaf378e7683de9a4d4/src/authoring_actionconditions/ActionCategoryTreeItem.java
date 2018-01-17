package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.Action;
import engine.Actions.ActionFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

/**
 * Class representing the first tree-item for building an action.
 * 
 * @author DavidTran
 *
 */
public class ActionCategoryTreeItem extends TreeItem<HBox> {

	private static final String ENTER_VALID_INPUT = "EnterValid";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";

	private ActionFactory actionFactory = new ActionFactory();
	private TreeItem<HBox> categoryAction = new TreeItem<HBox>();
	private ActionNameTreeItem actionName;
	private Runnable changeTreeViewSize;
	private String selectedCategory;
	private Supplier<List<AbstractSpriteObject>> supplier;

	/**
	 * Constructor
	 * 
	 * @param runnable
	 * @param supplier
	 */
	public ActionCategoryTreeItem(Runnable runnable, Supplier<List<AbstractSpriteObject>> supplier) {
		changeTreeViewSize = runnable;
		this.supplier = supplier;
		this.makeActionCategoryTreeItem();
	}

	/**
	 * 
	 * @return category Tree Item
	 */
	public TreeItem<HBox> getRootTreeItem() {
		return categoryAction;
	}

	/**
	 * 
	 * @return selected category
	 */
	public String getSelectedCategory() {
		return selectedCategory;
	}
	
	/**
	 * 
	 * @return selected action
	 */
	public String getSelectedAction() {
		return actionName.getSelectedAction();
	}
	
	/**
	 * 
	 * @return an action from the tree view
	 */
	public Action extract() {
		try {
			
			return actionName.extract();
		} catch (NullPointerException e) {
			// showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
			// return null;
			throw e;
		} catch (NumberFormatException e) {
			throw e;
		}

	}

	private TreeItem<HBox> makeActionCategoryTreeItem() {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action Category: "), makeActionCategoryChoiceBox(this));
		this.setValue(hb);
		this.setExpanded(true);
		this.expandedProperty().addListener(e -> changeTreeViewSize.run());
		return this;
	}

	private ChoiceBox<String> makeActionCategoryChoiceBox(TreeItem<HBox> categoryAction) {
		ObservableList<String> categories = FXCollections.observableList(actionFactory.getCategories());
		ChoiceBox<String> cb = new ChoiceBox<>(categories);
		;

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// ;
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				categoryAction.getChildren().clear();
				selectedCategory = categories.get(cb.getSelectionModel().getSelectedIndex());
				actionName = new ActionNameTreeItem(selectedCategory, supplier);
				categoryAction.getChildren().add(actionName);
			}
		});
		return cb;
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}
}
