package ActionConditionClasses;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public interface VBoxListI {
	
	public void changeLabel(String newLabel);
	public Object getCurrentValue();
	public void setNewOptions(ObservableList<Integer> newOptions);
	public int getOptionsSize();
	public void realizeNewOptions(ObservableList<Integer> newOptions);
	public ObservableList<Integer> getOptions();
	public void addListChangeListener(ListChangeListener<Integer> listChangeListener);
	public Label getLabel();

}
