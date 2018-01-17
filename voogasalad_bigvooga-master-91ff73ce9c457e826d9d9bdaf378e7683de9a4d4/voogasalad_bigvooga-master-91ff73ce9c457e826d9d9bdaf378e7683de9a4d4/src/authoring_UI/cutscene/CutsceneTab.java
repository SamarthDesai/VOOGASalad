package authoring_UI.cutscene;

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
 * Class that represents a tab listing cutscenes to edit
 * 
 * @author Dara Buggay
 *
 */
public class CutsceneTab extends DisplayableTab {

	private static final double CUTSCENE_SPACING = 25;
	private static final double PADDING = 25;

	private VBox cutsceneLister;
	private ScrollPane sp;

	public CutsceneTab(String name) {
		super(name);
		this.setContent(sp);
		cutsceneLister = new VBox();

		this.textProperty().bind(DisplayLanguage.createStringBinding(name));

		cutsceneLister = makeVBox((WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH) / 2, WelcomeScreen.HEIGHT,
				CUTSCENE_SPACING);
		cutsceneLister.setAlignment(Pos.TOP_CENTER);
		cutsceneLister.setPadding(new Insets(PADDING));

		sp = new ScrollPane();
		sp.setContent(cutsceneLister);
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
		if (cutsceneLister.getChildren().size() > index) {
			cutsceneLister.getChildren().remove(index);
			cutsceneLister.getChildren().add(index, btn);
		} else
			cutsceneLister.getChildren().add(btn);
		// System.out.println(dialogueLister.getChildren().size() + " " + index);

	}

	@Override
	protected void deleteDisplayable(int index) {
		cutsceneLister.getChildren().remove(index);

	}
	
	@Override
	protected int getButtonIndex(Button btn) {
		return cutsceneLister.getChildren().indexOf(btn);
	}

}
