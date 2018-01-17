package authoring_data;

import authoring_UI.MainAuthoringGUI;
import engine.utilities.data.GameDataHandler;

public class AuthoringDataManager {
	
	SpriteObjectGridToEngineController mySOGTEC;
	GameDataHandler myGDH;
	AuthoringMapDataManager myAMDM;
	MainAuthoringGUI myMM;
	
	AuthoringDataManager(GameDataHandler GDH){
		myGDH = GDH;
		createGridToEngineController();
		createMapDataManager();
	}
	
	private void createGridToEngineController(){
		mySOGTEC = new SpriteObjectGridToEngineController(myGDH);
	}
	
	private void createMapDataManager(){
		myAMDM = new AuthoringMapDataManager();
	}
	
	private void save(){
		;
	}
	
	private void load(){
		;
	}


}
