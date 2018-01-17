package authoring_UI.dialogue;

import authoring_UI.ViewSideBar;
import authoring_UI.displayable.DisplayableTab;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that represents a tab listing dialogues to edit
 * 
 * @author DavidTran and Dara Buggay
 *
 */
public class DialogueTab extends DisplayableTab {

	private static final double DIALOGUE_SPACING = 25;
	private static final double PADDING = 25;

	private VBox dialogueLister;
	private ScrollPane sp;

	public DialogueTab(String name) {
		super(name);
		dialogueLister = new VBox();

		this.textProperty().bind(DisplayLanguage.createStringBinding(name));

		dialogueLister = makeVBox((WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH) / 2, WelcomeScreen.HEIGHT,
				DIALOGUE_SPACING);
		dialogueLister.setAlignment(Pos.TOP_CENTER);
		dialogueLister.setPadding(new Insets(PADDING));

		sp = new ScrollPane();
		sp.setContent(dialogueLister);
		sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		this.setContent(sp);
	}


	@Override
	protected VBox makeVBox(double width, double height, double spacing) {
		return super.makeVBox(width, height, spacing);
	}

	@Override
	protected void addDisplayable(int index, Button btn) {
		if (dialogueLister.getChildren().size() > index) {
			dialogueLister.getChildren().remove(index);
			dialogueLister.getChildren().add(index, btn);
		} else
			dialogueLister.getChildren().add(btn);
	}

	@Override
	protected void deleteDisplayable(int index) {
		dialogueLister.getChildren().remove(index);

	}

	@Override
	protected int getButtonIndex(Button btn) {
		return dialogueLister.getChildren().indexOf(btn);
	}

}
