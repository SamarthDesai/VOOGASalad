package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * actionConditionVBox--a vbox a part of each action and condition tab that contains the list of action or condition rows. 
 * Purpose: to manage the actions and conditions of each spriteObject
 * Dependencies: on VBox, and an observable list of rows and interface
 * Example of how to use: create one with a pre-determined list of rows, or not if only a new one is desired
 * @author Owen Smith
 *
 * @param <T>--the list of action or condition rows
 */
public abstract class ActionConditionVBox<T> extends VBox implements ActionConditionVBoxI<T> {

	private ObservableList<T> rows;

	public ActionConditionVBox() {
		super();
		rows = FXCollections.observableArrayList();
		rows.addListener((ListChangeListener<T>) c -> addOrRemoveRows(c));
	}
	
	public ActionConditionVBox(List<T> rows) {
		this();
		this.rows.setAll(FXCollections.observableArrayList(rows));
	}
	
	/**
	 * getRows--returns a list of action or condition rows 
	 */
	@Override
	public List<T> getRows() {
		return rows;
	}
		
//		ActionConditionRow actionConditionRow = new ActionConditionRow(rows.size() + 1, label, selectorLabel,
//				isConditionVBox, currentActions, this);
//		rows.add(actionConditionRow);
//		if (!isConditionVBox) {
//			
//		} else
//			getChildren().add(actionConditionRow);
	//}

	/**
	 * removeConditionAction--removes a condition or action given a row number 
	 * row--an integer
	 */
	@Override
	public void removeConditionAction(int row) {
		rows.remove(row);
		for (int i = row; i < rows.size(); i++)
			((ActionConditionRow) rows.get(i)).decreaseLabelID();
	}
	
	/**
	 * addToRows--adds a condition/action row to the list at the end
	 */
	@Override
	public void addToRows(ActionConditionRow actionConditionRow) {
		rows.add((T) actionConditionRow);
	}
	
	private void addOrRemoveRows(Change<? extends T> c) {
		while(c.next()) {
			for(T row : c.getRemoved()) {
				getChildren().remove(row);
			}
			for(T row : c.getAddedSubList()) {
				getChildren().add((Node) row);
			}
		}
	}

}
