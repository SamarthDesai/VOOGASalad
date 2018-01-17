package controller.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.EngineController;
import engine.GameObject;
import engine.GameWorld;
import gui.player.GameDisplay;
import gui.welcomescreen.MenuOptionsTemplate;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class Debugging {

	private ScrollPane contentPane = new ScrollPane();

	private int BUTTON_GAP = 10;

	public static final String DEBUGGING_CSS = "Debugging.css";
	
	private Scene gameScene;
	private GameDisplay controller;
	private EngineController master;
	
	
	public Debugging(GameDisplay disp, Scene scene, EngineController cont)
	{
		gameScene = scene;
		controller = disp;
		master = cont;
	}
	
	


	public Scene createFrame(GameWorld currentWorld) {
		VBox contentBox = new VBox(BUTTON_GAP);
		contentBox.getChildren().addAll(getButtons(currentWorld.getAllGameObjects()));
		contentPane.setContent(contentBox);
		contentPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		contentPane.setStyle(WelcomeScreen.SET_BACKGROUND_COLOR + WelcomeScreen.BACKGROUND_COLOR);
		
		Scene debugScene = new Scene(contentPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
		debugScene.getStylesheets().add(MenuOptionsTemplate.class.getResource(DEBUGGING_CSS).toExternalForm());
		return debugScene;
		
	}

	private List<Button> getButtons(List<GameObject> allGameObjects) {
		DataView handler = new DataView();
		
		Map<String, List<String>> vars = handler.getData(allGameObjects);
		List<Button> buttons = new ArrayList<Button>();
		Button backButton = new Button("Go Back To Game");
		backButton.setOnMouseClicked(e->goBack());
		buttons.add(backButton);
		for(String s: vars.keySet())
		{
			Button button = new Button(s);
			button.setOnMouseClicked(e -> displayText(vars.get(s)));
			buttons.add(button);
		}
		return buttons;
	}
	
	/**
	 * Credit to user ItachiUchiha on StackOverflow for providing the base DialogBox code. https://stackoverflow.com/a/22167142
	 * @param s
	 */
	private void displayText(List<String> s)
	{
		//TODO: make good
		final Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        VBox dialogVbox = new VBox(BUTTON_GAP);
        for(String str: s)
        {
        	dialogVbox.getChildren().add(new Label(str));
        }
        ScrollPane scroller = new ScrollPane();
        scroller.setContent(dialogVbox);
        Scene dialogScene = new Scene(scroller, 300, 500);
        dialog.setScene(dialogScene);
        dialogScene.getStylesheets().add(MenuOptionsTemplate.class.getResource(DEBUGGING_CSS).toExternalForm());
        dialog.show();
	}
	private void goBack()
	{
		master.start();
		controller.setScene(gameScene);
		
	}

}
