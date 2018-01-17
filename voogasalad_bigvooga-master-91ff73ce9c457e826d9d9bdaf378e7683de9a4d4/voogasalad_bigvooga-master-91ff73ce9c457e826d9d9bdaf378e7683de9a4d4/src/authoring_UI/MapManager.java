package authoring_UI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.SpritePanels.GameElementSelector;
import authoring.SpritePanels.SpritePanels;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tools.DisplayLanguage;

/**
 * The primary class for the map editor; shows the list of sprites, dialogues, cutscenes, inventory, etc. that can be applied to sprites.
 * This class also contains the maps for the user to edit and build their games with. Each map is stored as a "world," which has 4 possible
 * layers: the Background, which can be any Image, GIF, or color, the Terrain, which contains sprite objects that act as another level of backdrop,
 * and the Main View, which is where all the modifiable and active sprite objects will be, and finally the Panels, which contain the HUD and game menu.
 * The map system is dynamic, and allows the user to also modify the map dimensions. When a user starts creating the game and begins dragging sprites
 * onto the map, they can alter the sprite to make it interact in its environment in numerous ways. When any sprite is selected, a display panel
 * with Tags, Parameters, Dialogues, Actions, Conditions, Inventory, Utility, and Animations will appear. These ultimately contain all the dynamic features of the sprite.
 * Firstly, the tags are the user-specified way of categorizing sprites to be referenced by conditions and actions and operations (i.e. there can be
 * various spike sprites with different names in a game, but to classify all of them as harmful in the same manner, the "spike" tag could be applied to the appropriate sprites,
 * and this tag can be used in conditions to state if a character collides with an object with the tag "spike," they lose 10 health).
 * The parameters are the defining states of the sprite, which involve completely flexible characteristics based on data types such as doubles,
 * booleans, etc. The Dialogues list the different sequences of dialogue that a sprite contains, which can be triggered based on Conditions and Actions.
 * The Conditions and Actions tab work in conjunction to allow users to define when different events will occur (i.e. a condition could be when a character,
 * has low health, and the corresponding action can be to reduce the speed of the character in the game). Inventory stores the objects that any
 * given sprite can contain. Utility has the map-specific properties of the sprite object, such as the cell width and height of the object,
 * the cell position of the sprite on the map, the file path of the sprite, and the UniqueID of the sprite that was generated as its reference.
 * Together, all these features create a very flexible and open environment for users to build games of a variety of styles, such as RPG,
 * puzzle, platformer, etc.
 * 
 * @author Samarth Desai, Sam Slack & Dara Buggay (Primary), and Archana Ahlawat
 *
 */
public class MapManager extends TabPane {
	public static final int VIEW_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;
	public static final int VIEW_HEIGHT = WelcomeScreen.HEIGHT - 35;
	protected String TAB_TAG;
	protected static final String ADD_TAB = "+";
	protected String MANAGER_NAME;

	protected Stage stage;
	protected Scene scene;
	protected SingleSelectionModel<Tab> mySelectModel;
	protected Tab addTab;
	protected ObjectProperty<Boolean> gridIsShowing;

	private ViewSideBar sideBar;
	private GameElementSelector mySprites;
	protected AuthoringEnvironmentManager myAEM;
	private int myTabCount = 1;
	private Tab startTab;
	private boolean oldProject;
	private String projectName = "TestProject";
	private List<DraggableGrid> allWorlds = new ArrayList<DraggableGrid>();
	private Pane mapEditor = new Pane();
	private SpritePanels spritePanels;
	private SpriteGridHandler mySpriteGridHandler;
	protected String myType;
	protected GameDataHandler GDH;
	
	public MapManager(AuthoringEnvironmentManager AEM, Scene currentScene, String type) {
		myType = type;
		setTabTag();
		setManagerName();
		gridIsShowing = new SimpleObjectProperty<Boolean>();

		myAEM = AEM;
		GDH = myAEM.getGameDataHandler();
		scene = currentScene;
		mapEditor.getChildren().add(this);
		mySelectModel = this.getSelectionModel();
		this.setPrefWidth(VIEW_WIDTH);
		this.setPrefHeight(VIEW_HEIGHT);
		this.setLayoutX(ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);

		List<DraggableGrid> DGs = getListOfDraggableGrids();
		createAddTab();
		if (DGs.size() > 0) {
			for (DraggableGrid w : DGs) {
				createTab(w);
			}
		} else {
			createTab(makeDraggableGrid());
		}
		this.mySelectModel.select(startTab);
	}

	public MapManager(AuthoringEnvironmentManager AEM, Scene currentScene) {
		this(AEM, currentScene, "");
		
	}

	protected void setManagerName() {
		MANAGER_NAME = "MapManager";
	}

	protected List<DraggableGrid> getListOfDraggableGrids() {
		List<DraggableGrid> DGs = GDH.loadWorldsFromWorldDirectory();
		return DGs;
	}

	protected String getManagerName() {
		return MANAGER_NAME;
	}
	
	public void addImportedWorlds(List<DraggableGrid> importedWorlds) {
		for (DraggableGrid w : importedWorlds) {
			createTab(w);
		}
		myAEM.getImportedSpriteController().changeFolderPath();
		myAEM.getImportedInventorySpriteController().changeFolderPath();
		System.out.println("ADD IMPORTED WORLDS");
	}

	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler) {
		return new SpritePanels(mySpriteGridHandler, myAEM);
	}

	protected DraggableGrid makeDraggableGrid() {
		return new DraggableGrid(GDH);
	}

	public void gridIsShowing() {
		gridIsShowing.set(true);
	}

	public void gridIsNotShowing() {
		gridIsShowing.set(false);
	}

	public void setGridIsShowing(boolean showing) {
		gridIsShowing.set(showing);
	}

	public boolean isGridShowing() {
		return gridIsShowing.get();
	}
	
	private void createAddTab(){
		this.setSide(Side.TOP);
		addTab = new Tab();
		addTab.setClosable(false);
		
		Button button = new Button();
		button.setText(ADD_TAB);
		button.setOnAction(e->{
			createTab(makeDraggableGrid());
		});
		addTab.setGraphic(button);

		this.getTabs().add(addTab);
	}


	private HBox setupScene(DraggableGrid w, Consumer<SpriteGridHandler> mySGHConsumer) {
		return setupFEAuthClasses(w, mySGHConsumer);
	}

	private HBox setupFEAuthClasses(DraggableGrid w, Consumer<SpriteGridHandler> mySGHConsumer) {
		allWorlds.add(w);
		SpriteGridHandler mySpriteGridHandler = new SpriteGridHandler(myTabCount, w);
		w.construct(mySpriteGridHandler);
		mySpriteGridHandler.addKeyPress(scene);
		spritePanels = makeSpritePanels(mySpriteGridHandler);
		mySpriteGridHandler.setGridDisplayPanel(spritePanels.getDisplayPanel());
		mySpriteGridHandler.setElementSelectorDisplayPanel(spritePanels.getElementSelectorDisplayPanel());
		mySGHConsumer.accept(mySpriteGridHandler);
		AuthoringMapEnvironment authMap = makeAuthoringMapEnvironment(spritePanels, w);
		return authMap;
	}

	protected AuthoringMapEnvironment makeAuthoringMapEnvironment(SpritePanels spritePanels, DraggableGrid dg) {
		return new AuthoringMapEnvironment(spritePanels, dg);
	}

	private void createTab(DraggableGrid w) {
		Tab newtab = createEditableTab();
		if (w.getName()==null){
			String newName = "World "+this.getTabs().size();
			((Label)newtab.getGraphic()).setText(newName);
			w.setName(newName);
		} else {
			((Label)newtab.getGraphic()).setText(w.getName());
		}
		newtab.setOnClosed(e -> this.removeWorld(w));
		newtab.setContent(setupScene(w, (SpriteGridHandler SGH)->{
		newtab.setOnSelectionChanged(change->{
				if (newtab.isSelected()){
					SGH.setGridIsShown(true);
				} else {
					SGH.setGridIsShown(false);
				}
			});
		}));
		
		((Label)newtab.getGraphic()).textProperty().addListener((change, oldValue, newValue)->{
			w.setName(newValue);
		});
		if (this.getTabs().size()==1){
			startTab = newtab;
		}
		this.getTabs().add(this.getTabs().size() - 1, newtab);
		myTabCount++;
		
		this.mySelectModel.select(newtab);
	}

	protected void setTabTag() {
		TAB_TAG = "Map";
	}

	private void removeWorld(DraggableGrid w) {
		allWorlds.remove(w);
		System.out.println("JUST REMOVED A WORLD, CURRENT SIZE IS: " + allWorlds.size());
		myTabCount--;
	}

	private List<AuthoringMapEnvironment> getAllMapEnvironments() {
		List<AuthoringMapEnvironment> allMaps = new ArrayList<AuthoringMapEnvironment>();
		for (Tab t : this.getTabs()) {
			if (!t.getText().equals(ADD_TAB)) {
				AuthoringMapEnvironment AME = (AuthoringMapEnvironment) t.getContent();
				allMaps.add(AME);
			}
		}
		return allMaps;
	}

	public Pane getPane() {
		return mapEditor;
	}

	public Tab getDialoguesTab() {
		return spritePanels.getDialoguesTab();
	}
	
	public Tab getCutscenesTab() {
		return spritePanels.getCutscenesTab();
	}

	public List<DraggableGrid> getAllWorlds() {
		System.out.println("SIZE OF ALL WORLDS: " + allWorlds.size()); // 3 even after I delete.
		return allWorlds;
	}

	private Tab createEditableTab() {
		StringProperty tabMap = new SimpleStringProperty();
		tabMap.bind(Bindings.concat(DisplayLanguage.createStringBinding(TAB_TAG))
				.concat(" " + Integer.toString(myTabCount)));
		//
		final Label label = new Label(tabMap.get());
		// cannot bind editable tab label!!

		final Tab tab = new Tab();
		tab.setGraphic(label);
		final TextField textField = new TextField();
		label.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (((MouseEvent) event).getClickCount() == 2) {
					textField.setText(label.getText());
					tab.setGraphic(textField);
					textField.selectAll();
					textField.requestFocus();
				}
			}
		});

		textField.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				label.setText(textField.getText());
				tab.setGraphic(label);
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					label.setText(textField.getText());
					tab.setGraphic(label);
				}
			}
		});
		return tab;
	}
}