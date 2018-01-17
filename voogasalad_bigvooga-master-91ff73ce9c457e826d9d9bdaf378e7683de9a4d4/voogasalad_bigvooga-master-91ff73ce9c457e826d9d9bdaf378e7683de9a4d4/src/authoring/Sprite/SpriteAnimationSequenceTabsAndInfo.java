package authoring.Sprite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import authoring.Sprite.AnimationSequences.AuthoringAnimationSequence;
import authoring.Sprite.AnimationSequences.AuthoringImageView;
import authoring.drawing.BoundingPolygonCreator;
//import authoring.drawing.BoundingPolygonCreator;
import engine.sprite.BoundedImage;
import engine.utilities.data.GameDataHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SpriteAnimationSequenceTabsAndInfo {

	private AbstractSpriteObject mySO;
	private VBox outmostContainerVBox;
	private List<AuthoringAnimationSequence> animationsSequences;
	private VBox tabPaneVbox;
	private TabPane containerTabPane;
	private HBox addAnimationSequenceHbox;
	private Button addAnimationSequenceButton;
	private Button createAnimationSequenceButton;
	private TextField promptNewName;
	private Label promptNameLabel;
	private Button addNewImage;
	private VBox animationVBox;
	private VBox buttonsVbox;
	private AuthoringAnimationSequence activeAnimationSeqeunce;
	private GameDataHandler GDH;

	public SpriteAnimationSequenceTabsAndInfo(GameDataHandler currentGDH) {
		initialize();
		GDH = currentGDH;
	}

	public void setSpriteObject(AbstractSpriteObject SO) {

		this.animationsSequences = new ArrayList<AuthoringAnimationSequence>();
		// clearAnimationSequencesList();
		// this.clearExistingAnimationSequencesTabPane();
		;
		mySO = SO;
		SO.getAnimationSequences().forEach(AS -> {
			;
			this.addAnimationSequence(AS);
		});
		;

	}

	private void initialize() {
		;
		initializeAnimationSequencesList();

		outmostContainerVBox = new VBox();

		tabPaneVbox = new VBox(10);
		tabPaneVbox.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));

		containerTabPane = new TabPane();
		containerTabPane.setSide(Side.RIGHT);
		containerTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		tabPaneVbox.getChildren().add(containerTabPane);
		// tabPaneVbox.getChildren().add(containerTabPane);

		animationVBox = new VBox(20);
		createAddImageButton();
		animationVBox.getChildren().addAll(this.addNewImage, createHBox());
		animationVBox.setAlignment(Pos.CENTER);

		outmostContainerVBox.getChildren().add(tabPaneVbox);

		// outmostContainerVBox.getChildren().add(tabPaneVbox);
		outmostContainerVBox.getChildren().add(this.animationVBox);

	}

	private void createAnimationSequenceButtons() {

		createAddAnimationSequenceHbox();
		createAddAnimationSequenceButton();
		createCreateAnimationSequenceButton();
		createPromptNameText();
		createPromptNameLabel();
		createButtonsVBox();
		putAddAnimationSequenceButtonIntoHbox();

	}

	private void createAddImageButton() {
		addNewImage = new Button("Add Image");
		addNewImage.setOnAction(event -> {
			Node parent = addNewImage.getParent();
			Scene s = parent.getScene();
			while (s == null) {
				parent = parent.getParent();
				s = parent.getScene();
			}

			File file = GameDataHandler.chooseFileForImageLoad(s.getWindow());
			Image im = GDH.getImage(file);
			AuthoringImageView AIV = new AuthoringImageView(file.getName(), GDH);
			makeBoundedImagePopup(AIV, im, file.getName());
			addNewAuthoringImageViewToSequence(this.activeAnimationSeqeunce, AIV);
		});
	}
	
	private void makeBoundedImagePopup(AuthoringImageView view, Image im, String name) {
		Stage popup = new Stage();
		view.setBoundedImage(new BoundedImage(name));
		BoundingPolygonCreator bpc = new BoundingPolygonCreator(im, name, bi -> view.setBoundedImage(bi));
		popup.setScene(new Scene(bpc));
		popup.setOnCloseRequest(e -> bpc.save());
		popup.show();
	}

	private void addNewAuthoringImageViewToSequence(AuthoringAnimationSequence AAS, AuthoringImageView AIV) {
		AAS.addNewAuthoringImageViewToSequence(AIV, false);
	}

	public Button getAddImageButton() {
		if (addNewImage == null) {
			createAddImageButton();
		}
		return addNewImage;
	}

	private void initializeAnimationSequencesList() {
		animationsSequences = new ArrayList<AuthoringAnimationSequence>();
	}

	private void clearAnimationSequencesList() {
		animationsSequences = new ArrayList<AuthoringAnimationSequence>();
	}

	// private void createAnimationSequenceTabPane(){
	// containerTabPane = new TabPane();
	// containerTabPane.setSide(Side.RIGHT);
	// containerTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	//
	// }

	// private TabPane getAnimationTabPane(){
	// return containerTabPane;
	// }

	public void clearExistingAnimationSequencesTabPane() {
		;
		containerTabPane.getTabs().clear();
	}

	private void addToVBox(Pane pane) {
		tabPaneVbox.getChildren().add(pane);
	}

	private void clearVBox() {
		tabPaneVbox.getChildren().clear();
	}

	/**
	 * @return Pane - the surrounding VBox for the TabPane of AnimationSequences
	 */
	public Pane getTabPaneVbox() {
		;
		return tabPaneVbox;
	}

	/**
	 * @return HBox to put the buttons and text in to make a new AnimationSequence
	 */
	public HBox createHBox() {
		createAnimationSequenceButtons();
		return addAnimationSequenceHbox;
	}

	// private void createContainerVBox(){
	// tabPaneVbox = new VBox(10);
	// tabPaneVbox.setBorder(new Border(new BorderStroke(Color.BLACK,
	// BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
	// }

	// private void createOutmostVBox(){
	// ;
	// outmostContainerVBox = new VBox();
	// }

	/**
	 * @return VBox - the UI view for this entire class - a tab pane of
	 *         AnimationSequences and buttons to add to them
	 */
	public VBox getAnimationBox() {
		;
		// System.out.println("Content:
		// "+((VBox)containerScrollPane.getContent()).getChildren());

		return this.outmostContainerVBox;
	}

	// public void putVBoxIntoScrollPane(){
	//
	// }

	private void createAddAnimationSequenceHbox() {
		addAnimationSequenceHbox = new HBox(10);
		addAnimationSequenceHbox.setAlignment(Pos.CENTER);
	}

	private void createAddAnimationSequenceButton() {
		addAnimationSequenceButton = new Button("Create Animation Sequence");
		addAnimationSequenceButton.setOnAction(event -> {
			this.removeAddAnimationSequenceButtonFromHbox();
			this.addPromptNewNameAndCreateButtonToHbox();
		});

	}

	private void createCreateAnimationSequenceButton() {
		createAnimationSequenceButton = new Button("Create");
		createAnimationSequenceButton.setOnAction(event -> {

			String animationSeqName = promptNewName.getText();
			if (!nameIsValid(animationSeqName)) {
				promptNewName.setText("This Name Already Used");
			} else {
				AuthoringAnimationSequence newSequence = new AuthoringAnimationSequence(animationSeqName);
				Tab newTab = addAnimationSequence(newSequence);
				this.containerTabPane.getSelectionModel().select(newTab);
				this.removePromptNewNameAndCreateButtonToHbox();
				this.putAddAnimationSequenceButtonIntoHbox();

			}

		});

	}

	private boolean nameIsValid(String newName) {
		return !mySO.getAnimationSequences().stream().anyMatch(AS -> AS.getName().equals(newName));

	}

	private Tab addAnimationSequence(AuthoringAnimationSequence AS) {

		this.removePromptNewNameAndCreateButtonToHbox();
		this.putAddAnimationSequenceButtonIntoHbox();

		AuthoringAnimationSequence dummyAS = new AuthoringAnimationSequence(AS);
		this.animationsSequences.add(dummyAS);
		Tab tab = new Tab();
		tab.setText(dummyAS.getName());
		tab.setContent(dummyAS.getUIContent());
		tab.setOnSelectionChanged((event) -> {
			if (tab.isSelected()) {
				activeAnimationSeqeunce = dummyAS;
			}
		});
		// tab.sele
		;
		;
		containerTabPane.getTabs().add(tab);

		return tab;
	}

	private void createPromptNameLabel() {
		promptNameLabel = new Label("Animation Sequence Name:");

	}

	private void createPromptNameText() {
		promptNewName = new TextField();

	}

	private void putAddAnimationSequenceButtonIntoHbox() {
		addAnimationSequenceHbox.getChildren().clear();
		this.addAnimationSequenceHbox.getChildren().add(addAnimationSequenceButton);
	}

	private void createButtonsVBox() {
		buttonsVbox = new VBox(15);
	}

	private void removeAddAnimationSequenceButtonFromHbox() {
		this.addAnimationSequenceHbox.getChildren().remove(addAnimationSequenceButton);
	}

	private void addPromptNewNameAndCreateButtonToHbox() {
		this.addAnimationSequenceHbox.getChildren().addAll(promptNameLabel, promptNewName,
				createAnimationSequenceButton);
	}

	private void removePromptNewNameAndCreateButtonToHbox() {
		this.addAnimationSequenceHbox.getChildren().removeAll(promptNameLabel, promptNewName,
				createAnimationSequenceButton);
	}

	public void apply() {
		mySO.setAnimationSequences(this.animationsSequences);
	}

}
