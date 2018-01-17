package authoring_UI.dialogue;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

/**
 * Class that holds all dialogue tabs
 * 
 * @author DavidTran and Dara Buggay
 *
 */
public class DialogueTabPane extends TabPane {

	private static final String DEFAULT = "DefaultDialogues";
	private static final String USER = "UserCreatedDialogues";
	private DefaultDialogueTab defaultTab;
	private UserDialogueTab userTab;

	public DialogueTabPane() {
		
		defaultTab = new DefaultDialogueTab(DEFAULT);
		userTab = new UserDialogueTab(USER);
		this.getTabs().addAll(defaultTab, userTab);
	}
	
	public void addDefaultDialogueButton(int number, Button btn) {
		defaultTab.addDisplayable(number, btn);
	}
	
	public void addUserDialogueButton(int number, Button btn) {
		userTab.addDisplayable(number, btn);
	}

	public void removeUserDialogueButton(int id) {
		userTab.deleteDisplayable(id);
	}
	
	public int getButtonIndex(Button btn) {
		return userTab.getButtonIndex(btn);
	}

}
