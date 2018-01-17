package authoring_actionconditions;

import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import ActionConditionClasses.ResourceBundleUtil;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterSidebarManager;
import authoring.Sprite.AbstractSpriteObject;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * ActionTab--the tab containing all of the UI components for creating actions for a selected sprite object. It contains a top HBox to remove or create
 * actions and all of the actionrows containing information about each action
 * Assumptions: the action tab will parametized with actionrows
 * Dependencies: depends on the Tab class and depends on the actionTab interface (really this should not exist, and instead should just have public 
 * methods)
 * Example of how to use: create an action tab. Have the option to set its action vbox or actionHBox differently if loading from a spriteObject or 
 * from a saved game
 * @author Owen Smith
 *
 * @param <T>
 */
public class ActionTab<T> extends Tab implements ActionTabI<T> {

	private static final double SPACING = 10;

	private ScrollPane actionConditionManager;
	private ActionConditionHBox buttons;
	private ActionConditionVBox<T> actionConditionVBox;
	private ResourceBundle actionTabResources;
	private VBox mainVBox;
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ActionTab(String title,Supplier<List<AbstractSpriteObject>> supplier) {
		super(title);
		this.supplier = supplier;
		actionTabResources = ResourceBundleUtil.getResourceBundle(title);
		actionConditionManager = new ScrollPane();
		setContent(actionConditionManager);
		setUpActionConditionManager(title);
	}

//	public ActionTab(String title, ActionConditionVBox<T> actionConditionVBox, ActionConditionHBox topToolBar) {
//		this(title);
//		mainVBox.getChildren().removeAll(this.actionConditionVBox, this.buttons);
//		this.actionConditionVBox = actionConditionVBox;
//		buttons = topToolBar;
//		mainVBox.getChildren().addAll(this.buttons, this.actionConditionVBox);
//		//myAEM = AEM;	TODO maybe incorporate the AEM?
//	}
	
	public Supplier<List<AbstractSpriteObject>> getSupplier() {
		System.out.println("IS SUPPLIER NULL WHEN CALLING GETTING??? ");
		System.out.println(supplier == null);
		return supplier;
	}

	private void setUpActionConditionManager(String title) {
		buttons = new ActionConditionHBox(title);
		actionConditionVBox = setActionConditionVBox();
		mainVBox = new VBox(SPACING);
		mainVBox.getChildren().addAll(buttons, actionConditionVBox);
		actionConditionManager.setContent(mainVBox);
	}

	/**
	 * addTopToolBarListChangeListener--adds a listener for the actionConditionHBox to listen for if the remove or add action button is clicked 
	 */
	@Override
	public void addTopToolBarListChangeListener(ListChangeListener<Integer> listChangeListener) {
		buttons.addRemoveRowVBoxListener(listChangeListener);
	}

	/**
	 * getCurrentActions--returns the list of all current action options so that, when another condition row is created, it is created with however 
	 * many action options are returned here 
	 */
	@Override
	public ObservableList<Integer> getCurrentActions() {
		return buttons.getRemoveRowVBoxOptions();
	}

	/**
	 * addAction--calls the VBox to add an action
	 */
	@Override
	public void addAction() {
		((ActionVBox<T>) actionConditionVBox).addAction();
	}

	/**
	 * addRemoveOption--calls the topactionHbox to remove an action from its list
	 */
	@Override
	public void addRemoveOption() {
		buttons.addRemoveOption();
	}

	/**
	 * removeActionCondition--calls the vbox to remove an action 
	 */
	@Override
	public void removeActionCondtion(Integer row) {
		actionConditionVBox.removeConditionAction(row);
	}

	/**
	 * removeRowOption--calls the topHBox to remove an option for an action to remove
	 */
	@Override
	public void removeRowOption(Integer row) {
		buttons.removeRemoveOption(row);
	}

	/**
	 * getRemove Value--returns the value needed to be removed 
	 */
	@Override
	public Integer getRemoveValue() {
		return buttons.getRemoveValue();
	}

	/**
	 * addButtonListener--adds a listener for the "add action" button in the top HBox so that a new action row and treeview can be created
	 */
	@Override
	public void addButtonListener(EventHandler<ActionEvent> e) {
		buttons.addButtonListener(e);
	}

	/**
	 * addRemove listener--adds a listener for the remove button in the top HBox so that, if the button is clicked, the appropriate action will be 
	 * removed and those changes will also be reflected in the condition rows
	 */
	@Override
	public void addRemoveListener(EventHandler<ActionEvent> e) {
		buttons.addRemoveListener(e);
	}

	/**
	 * getActionConditionVBox--gets the actionVBox 
	 */
	@Override
	public ActionConditionVBox<T> getActionConditionVBox() {
		return actionConditionVBox;
	}

	/**
	 * getTopToolbar--gets the actionHBox at the top of the tab
	 */
	@Override
	public ActionConditionHBox getTopToolBar() {
		return buttons;
	}

	/**
	 * getSelectorLabel--method did not end up geing used
	 */
	@Override
	public String getSelectorLabel() {
		return actionTabResources.getString("SelectorLabel");
	}

	/**
	 * setActionCondition--when loading an action or condition from a sprite object, sets the vBox to what it would be for the spriteObject
	 */
	@Override
	public ActionConditionVBox<T> setActionConditionVBox() {
		return new ActionVBox<T>(supplier);
	}

	/**
	 * setTopToolBar--sets the top ActionHbox with the list of actions loaded from the spriteObject 
	 */
	@Override
	public void setTopToolBar(ActionConditionHBox topToolBar) {
		mainVBox.getChildren().removeAll(buttons, actionConditionVBox);
		buttons = topToolBar;
		mainVBox.getChildren().addAll(buttons, actionConditionVBox);
	}

	/**
	 * setNoReturnActionConditionVBox--sets the actionConditionVBox to a new actionConditionVBox if the user is loading information from a sprite
	 */
	@Override
	public void setNoReturnActionConditionVBox(ActionConditionVBox<T> actionConditionVBoxNew) {
		mainVBox.getChildren().remove(actionConditionVBox);
		this.actionConditionVBox = actionConditionVBoxNew;
		mainVBox.getChildren().add(actionConditionVBox);
	}

}
