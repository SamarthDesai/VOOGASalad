package authoring.SpritePanels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import ActionConditionClasses.ApplyButtonController;
import ActionConditionClasses.ResourceBundleUtil;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterSidebarManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteAnimationSequenceTabsAndInfo;
import authoring.Sprite.SpriteInventoryTabAndInfo;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.SpriteParameterTabsAndInfo;
import authoring.Sprite.SpriteTagTabAndInfo;
import authoring.Sprite.SpriteUtilityTabAndInfo;
import authoring.Sprite.DialogTab.SpriteDialogTabAndInfo;
import authoring.Sprite.Parameters.SpriteParameter;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring_UI.MainAuthoringGUI;
import authoring_UI.ViewSideBar;
import authoring_actionconditions.ActionRow;
import authoring_actionconditions.ActionTab;
import authoring_actionconditions.ConditionRow;
import authoring_actionconditions.ConditionTab;
import authoring_actionconditions.ControllerConditionActionTabs;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class DisplayPanel extends VBox {
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;
	private VBox myParamTabVBox;
	private TextArea myParameterErrorMessage;
	private ConditionTab<ConditionRow> conditions;
	private ActionTab<ActionRow> actions;
	private ApplyButtonController applyButtonController;
	private SpriteParameterTabsAndInfo mySParameterTAI;
	private SpriteInventoryTabAndInfo mySInventoryTAI;
	private SpriteUtilityTabAndInfo mySUtilityTAI;
	private SpriteDialogTabAndInfo mySDialogTAI;
	private SpriteAnimationSequenceTabsAndInfo mySAnimationSequenceTAI;
	private SpriteTagTabAndInfo mySTagTAI;
	private ObjectProperty<Boolean> multipleCellsActiveProperty;
	private VBox spriteEditorAndApplyButtonVBox;
	protected AbstractSpriteObject activeSprite;
	private ControllerConditionActionTabs controllerConditionActionTabs;
	private static final String ACTIONCONDITIONTITLES_PATH = "TextResources/ConditionActionTitles";
	private static final double DISPLAY_PANEL_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH / 2
			- ViewSideBar.VIEW_MENU_HIDDEN_WIDTH - 155;
	private static final double DISPLAY_PANEL_HEIGHT = 347;
	private static final int CONDITIONTAB_INDEX = 3;
	private static final int ACTIONTAB_INDEX = 4;
	private SpriteParameterSidebarManager mySPSM;
	private AuthoringEnvironmentManager myAEM;

	public static final ResourceBundle conditionActionTitles = ResourceBundle.getBundle(ACTIONCONDITIONTITLES_PATH);
	// private SpriteSetHelper mySSH;

	public DisplayPanel(SpriteParameterSidebarManager SPSM, AuthoringEnvironmentManager AEM) {
		this(AEM);
		mySPSM = SPSM;
		
	}

	public DisplayPanel(AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		multipleCellsActiveProperty = new SimpleObjectProperty<Boolean>();
		mySParameterTAI = new SpriteParameterTabsAndInfo();
		mySInventoryTAI = new SpriteInventoryTabAndInfo(myAEM);
		mySAnimationSequenceTAI = new SpriteAnimationSequenceTabsAndInfo(myAEM.getGameDataHandler());
		mySUtilityTAI = new SpriteUtilityTabAndInfo();
		mySTagTAI = new SpriteTagTabAndInfo();
		mySDialogTAI = new SpriteDialogTabAndInfo(myAEM);
		System.out.println("made SPTAI in MENU");
		setUpMenu();
	}

	private void setErrorMessage() {
		myParameterErrorMessage = new TextArea("Either no active cells or active cells have different parameters");
	}

	public AbstractSpriteObject setActiveSprite(AbstractSpriteObject ASO) {
		if (!activeSprite.equals(ASO)) {
			AbstractSpriteObject prevActive = activeSprite;
			activeSprite = ASO;
			return prevActive;
		}
		activeSprite = null;
		return null;
	}

	private void setSpriteInfoAndVBox() {
		spriteEditorAndApplyButtonVBox = new VBox();
		spriteEditorAndApplyButtonVBox.getChildren().addAll(mySpriteTabs, this.makeApplyButton());
	}

	private Map<String, List<SpriteParameter>> getParametersOfActiveCells() throws Exception {
		return mySPSM.getActiveSprite().getParameters();
	}


	protected AbstractSpriteObject getActiveCell() throws Exception {
		// ;
		return mySPSM.getActiveSprite();
	}

	protected void checkMultipleCellsActive() {
		this.multipleCellsActiveProperty.set(mySPSM.multipleActive());
	}
	
	protected void setMultipleCellsActive(boolean hasMultipleActive) {
		this.multipleCellsActiveProperty.set(hasMultipleActive);
	}

	private void setUpMenu() {
		setErrorMessage();
		createParameterCategoryTabs();
		createSpriteTabs();
		// createSpriteCreator();
		this.setPrefSize(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT);
		setSpriteInfoAndVBox();
		
		// createStatePane(new VBox());
	}

	private void createActionConditionTabs() {
		conditions = new ConditionTab<ConditionRow>(ResourceBundleUtil.getTabTitle("ConditionsTabTitle"), () -> mySPSM.getAllSpritesFromActiveGrid());
		actions = new ActionTab<ActionRow>(ResourceBundleUtil.getTabTitle("ActionsTabTitle"), () -> mySPSM.getAllSpritesFromActiveGrid());
		controllerConditionActionTabs = new ControllerConditionActionTabs(conditions, actions);
		applyButtonController = new ApplyButtonController();
		mySpriteTabs.getTabs().addAll(conditions, actions);
	}

	private void createParameterTab() {
		Tab parameters = new Tab("Parameters");
		parameters.setContent(createContainingVBoxToPlaceInParameterTab());
		mySpriteTabs.getTabs().addAll(parameters);
	}

	private void createDialogueTab() {
		Tab dialogue = new Tab("Dialogue");
		dialogue.setContent(mySDialogTAI.getContainingVBox());
		
//		dialogue.setContent(new TextArea("dialogue goes here"));
		mySpriteTabs.getTabs().addAll(dialogue);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			dialogue.setDisable(newStatus);
		});
	}

	private void createInventoryTab() {
		Tab inventory = new Tab("Inventory");
		inventory.setContent(mySInventoryTAI.getContainingVBox());
		mySpriteTabs.getTabs().addAll(inventory);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			inventory.setDisable(newStatus);
		});
	}

	private void createUtilityTab() {
		Tab utility = new Tab("Utility");
		utility.setContent(mySUtilityTAI.getScrollPane());
		mySpriteTabs.getTabs().addAll(utility);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			utility.setDisable(newStatus);
		});
	}

	private void createAnimationTab() {
		Tab animations = new Tab("Animations");
		animations.setContent(mySAnimationSequenceTAI.getAnimationBox());
		mySpriteTabs.getTabs().add(animations);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			animations.setDisable(newStatus);
		});
	}

	private void createTagTab() {
		Tab tags = new Tab("Tags");
		tags.setContent(mySTagTAI.getContainingVBox());
		mySpriteTabs.getTabs().add(tags);
//		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
//			tags.setDisable(newStatus);
//		});
	}

	private void createSpriteTabs() {
		mySpriteTabs = new TabPane();
		mySpriteTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		mySpriteTabs.setSide(Side.TOP);
		createTagTab();
		createParameterTab();
		createDialogueTab();
		createActionConditionTabs();
		createInventoryTab();
		createUtilityTab();
		createAnimationTab();
		// .setOnSelectionChanged(e->{displayParams();});
		// parameters.setContent(myParamTabs);
		// mySpriteTabs.getTabs().addAll(parameters, dialogue);

	}

	/**
	 * Creates the VBox that will contain the TabPane of the Active Sprite's
	 * parameters This VBox will be the content of the Tab that says parameters.
	 * 
	 * @author Samuel
	 * @return VBox - the VBox to contain Sprite Parameter Info
	 */
	private VBox createContainingVBoxToPlaceInParameterTab() {
		myParamTabVBox = new VBox();

		myParamTabVBox.getChildren().addAll(myParamTabs);

		setDefaultErrorNoSpriteTabPane();

		// theHorizTabs = myParamTabs;

		// myParamTabVBox.getChildren().addAll(theHorizTabs, applyButton);
		// addParameterErrorMessage();
		return myParamTabVBox;
	}

	/**
	 * Make's the apply button that the user will click to apply changes the
	 * Active Sprite Makes button's set on action call the 'apply' method in
	 * this class.
	 * 
	 * @author Samuel
	 * @return Button - the button UI component.
	 */
	private Button makeApplyButton() {
		Button applyButton = new Button();
		applyButton.textProperty().setValue("Apply");
		applyButton.setOnAction(e -> {
			try {
				apply();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		return applyButton;
	}

	/**
	 * Ensures that the instance variable containing the TabPane of the Active
	 * Sprite's Parameters is pointed to the class controlling that info.
	 * 
	 * @author Samuel
	 * 
	 */
	private void createParameterCategoryTabs() {
		// mySPTAI.createCategoryTabs();
		myParamTabs = mySParameterTAI.getTabPane();

		// myParamTabs = new TabPane();
		// myParamTabs.setSide(Side.RIGHT);
		// myParamTabs.setPrefHeight(500);
		// myParamTabs.setPrefWidth(400);

	}

	private void clearAllSpriteEditorTabs() {
		// myParamTabs.getTabs().clear();
		mySParameterTAI.clearTabPane();
		mySInventoryTAI.reset();
		// mySUtilityTAI.reset();
		mySAnimationSequenceTAI.clearExistingAnimationSequencesTabPane();
	}

	public void removeSpriteEditorVBox() {
		if (this.getChildren().contains(spriteEditorAndApplyButtonVBox)) {
			this.getChildren().remove(spriteEditorAndApplyButtonVBox);
		}
	}

	public void addSpriteEditorVBox() {
		if (!this.getChildren().contains(spriteEditorAndApplyButtonVBox)) {
			this.getChildren().addAll(spriteEditorAndApplyButtonVBox);
		}
	}

	private void setDefaultErrorNoSpriteTabPane() {
		clearAllSpriteEditorTabs();
		removeSpriteEditorVBox();
		addSpriteEditorErrorMessage();
	}

	private void removeSpriteEditorErrorMessage() {
		if (this.getChildren().contains(myParameterErrorMessage)) {
			this.getChildren().remove(myParameterErrorMessage);
		}
	}

	private void addSpriteEditorErrorMessage() {
		if (!this.getChildren().contains(myParameterErrorMessage)) {
			// ;
			int numChildren = this.getChildren().size();
			this.getChildren().add(numChildren, myParameterErrorMessage);
		}
	}

	public void updateParameterTab() {

		;
		try {
			AbstractSpriteObject activeCell = getActiveCell();
			if (activeCell!=null){
			checkMultipleCellsActive();
			clearAllSpriteEditorTabs();
			removeSpriteEditorErrorMessage();
			// mySParameterTAI.create(getActiveCell());
			mySParameterTAI.create(activeCell);
			mySTagTAI.setSpriteObjectAndUpdate(activeCell);
			if (!multipleActive()) {
				applyButtonController.updateActionConditionTabs(conditions,actions, activeCell);
				controllerConditionActionTabs = new ControllerConditionActionTabs(conditions, actions);
				mySpriteTabs.getTabs().set(CONDITIONTAB_INDEX, conditions);
				mySpriteTabs.getTabs().set(ACTIONTAB_INDEX, actions);
				mySDialogTAI.setSpriteObject(activeCell);
				System.out.println("Trying to update not multiple actvie");
				mySInventoryTAI.setSpriteObjectAndUpdate(activeCell);
				mySUtilityTAI.setSpriteObjectAndUpdate(activeCell);
				mySAnimationSequenceTAI.setSpriteObject(activeCell);
			}
			addSpriteEditorVBox();
			} else {
				setDefaultErrorNoSpriteTabPane();
			}
		} catch (Exception e) {
			setDefaultErrorNoSpriteTabPane();
			// throw new RuntimeException();
			e.printStackTrace();
			
		}
		this.setPrefWidth(DISPLAY_PANEL_WIDTH);
	}

	protected boolean multipleActive() {
		return this.multipleCellsActiveProperty.get();
	}

	private ScrollPane createStatePane(VBox temp) {
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setPrefSize(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT);
		myStateSP_dummy.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
	}

	// private void buttonInteraction() {
	// // TODO
	// }

	private void apply() throws Exception {
		mySParameterTAI.apply();
		mySTagTAI.apply();
		if (!multipleActive()) {
			System.out.println("Trying to set ivent etc.");
			mySDialogTAI.apply();
			mySInventoryTAI.apply();
			mySAnimationSequenceTAI.apply();
			applyButtonController.updateSpriteObject(conditions, actions,getActiveCell());
		}	
		applyToMultipleAtOnce();
	}
	
	protected void applyToMultipleAtOnce(){
		mySPSM.apply();
	}

	private void addParameter() {
		List<String> choices = new ArrayList<>();
		choices.add("Boolean");
		choices.add("String");
		choices.add("Double");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Boolean", choices);
		dialog.setTitle("Add Parameter");
		dialog.setContentText("Choose Parameter Type:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(type -> createNewParameter(type));
	}

	private void createNewParameter(String type) {
	}
}
