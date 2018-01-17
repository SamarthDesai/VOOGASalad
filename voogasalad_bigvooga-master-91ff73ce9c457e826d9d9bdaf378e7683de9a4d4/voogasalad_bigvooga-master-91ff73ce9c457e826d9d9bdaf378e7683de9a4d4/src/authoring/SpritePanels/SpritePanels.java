package authoring.SpritePanels;

import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterSidebarManager;
import authoring_UI.SpriteGridHandler;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class SpritePanels extends VBox {

	protected DisplayPanel displayPanel;
	protected DisplayPanel elementSelectorDisplayPanel;
	protected GameElementSelector gameElementSelector;
	protected SpriteParameterSidebarManager SPSM;
	protected String myType;
//	private SpriteSetHelper mySpriteSetHelper;
	
	public SpritePanels(){
		
	}
	
	public SpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM){
		this(mySGH, myAEM, "");
	}

	public SpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM, String type) {
//		Map<String, List<Pane>> thumbnailSprites = myAEM.getEveryTypeOfSpriteAsThumbnails();
//		mySpriteSetHelper = new SpriteSetHelper(thumbnailSprites);
		myType = type;
		SPSM = new SpriteParameterSidebarManager(mySGH.getDraggableGrid());
		makeLayerDisplayPanel(myAEM);
		makeElementSelector(mySGH, myAEM);
		makeElementSelectorDisplayPanel(myAEM);
		this.getChildren().addAll(displayPanel, gameElementSelector);
		this.setSpacing(5);
		
	}
	
	private void switchDisplayPanel(DisplayPanel DP){
		this.getChildren().remove(0);
		this.getChildren().add(0, DP);
	}
	
	public void makeLayerDisplayPanel(AuthoringEnvironmentManager myAEM){
		displayPanel = new DisplayPanel(SPSM, myAEM);  
	}
	
	public void makeElementSelectorDisplayPanel(AuthoringEnvironmentManager myAEM){
		;
		elementSelectorDisplayPanel = new DisplayPanelForTemplateSprites(myAEM);  
		((DisplayPanelForTemplateSprites) elementSelectorDisplayPanel).setOnElementSpriteActive(new Consumer<Boolean>(){

			@Override
			public void accept(Boolean t) {
				if (t){
					elementSelectorDisplayPanel.updateParameterTab();
					switchDisplayPanel(elementSelectorDisplayPanel);
				} else {
					elementSelectorDisplayPanel.updateParameterTab();
					switchDisplayPanel(displayPanel);
				}
				
			}
			
		});
	}
	
	public void makeElementSelector(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM){
		gameElementSelector = new GameElementSelector(mySGH, myAEM);
	}
	
	public DisplayPanel getElementSelectorDisplayPanel(){
		return elementSelectorDisplayPanel;
	}
	
	public DisplayPanel getDisplayPanel() {
		return displayPanel;
	}
	
	public Tab getDialoguesTab() {
		return gameElementSelector.getDialoguesTab();
	}
	
	public Tab getCutscenesTab() {
		return gameElementSelector.getCutscenesTab();
	}
}