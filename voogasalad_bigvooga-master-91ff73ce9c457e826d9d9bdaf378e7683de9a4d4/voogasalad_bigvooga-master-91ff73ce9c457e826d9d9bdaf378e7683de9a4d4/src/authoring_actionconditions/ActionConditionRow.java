package authoring_actionconditions;

import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import ActionConditionClasses.ChoiceBoxVBox;
import authoring.Sprite.AbstractSpriteObject;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import tools.DisplayLanguage;

/**
 * ActionConditionRow--a super class that both action and conditions rows implement since they both have features in common. One feature common amongst
 * them is having a number label corresponding to the number action or condition. This number can be decreased if a row from the HBox is removed
 * @author Owen Smith
 *
 */

public class ActionConditionRow extends ToolBar implements ActionConditionRowI {
	
	protected static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";
	protected static final double ROW_WIDTH = 650;
	protected static final double TREE_VIEW_WIDTH = 550;
	protected static final double EXPANDED_HEIGHT = 350;
	protected static final double COLLAPSED_HEIGHT = 25;

	protected ResourceBundle actionConditionVBoxResources;
	private int labelInt;
	private Label label;
	private Label IDlabel;
	private ChoiceBoxVBox implementationSelectorVBox;
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ActionConditionRow(int ID, ActionConditionVBox<?> ACVBox,Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
		labelInt = ID;
		IDlabel = new Label(Integer.toString(ID));
		this.supplier = supplier;
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		getItems().addAll(IDlabel, separator);
	}

	// public ActionConditionRow(int ID, String label, String selectorLabel, String
	// selectedConditionAction,
	// ActionConditionVBox<?> ACVBox) {
	// super();
	// actionConditionVBoxResources =
	// ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
	// this.label = new Label(label);
	// labelInt = ID;
	// IDlabel = new Label(Integer.toString(ID));
	// Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
	// ObservableList<String> actionConditionOptions =
	// ActionConditionTabUtil.convertToObservableList(
	// actionConditionVBoxResources.getString(label +
	// actionConditionVBoxResources.getString("OptionsTag")));
	// implementationSelectorVBox = new ChoiceBoxVBox<String>(selectorLabel,
	// actionConditionOptions);
	// implementationSelectorVBox.setValue(selectedConditionAction);
	// getItems().addAll(IDlabel, separator, this.label);
	// }
	
	/**
	 * getSupplier--
	 * @return
	 */
	public Supplier<List<AbstractSpriteObject>> getSupplier() {
		return supplier;
	}

	/**
	 * decreaseLabelID--decreases the label ID by one if another row below it is removed
	 */
	@Override
	public void decreaseLabelID() {
		labelInt--;
		IDlabel.setText(Integer.toString(labelInt));
	}

	/**
	 * getImplementationSelectorVBoxValue--returns the value of the choiceBox for removing a row
	 */
	@Override
	public Integer getImplementationSelectorVBoxValue() {
		return implementationSelectorVBox.getCurrentValue();
	}

	/**
	 * getLabel--returns the label of the action or condition row, which is its number
	 */
	@Override
	public Label getLabel() {
		return label;
	}

	/**
	 * getImplementationSelectorLabel--this method did not get used in the program
	 */
	@Override
	public Label getImplementationSelectorLabel() {
		return implementationSelectorVBox.getLabel();
	}

	/**
	 * getRowID--same thing as getLabel, there should not be two methods for this
	 */
	@Override
	public int getRowID() {
		return labelInt;
	}

}
