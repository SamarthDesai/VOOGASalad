package authoring_UI.SpriteCreatorTab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteNameManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.Sprite.SpriteObject;
import authoring.drawing.ImageCanvasPane;
import authoring_UI.AuthoringMapStackPane;
import authoring_UI.MainAuthoringGUI;
import authoring_UI.SpriteGridHandler;
import authoring_UI.ViewSideBar;
import engine.VoogaException;
import engine.utilities.data.GameDataHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.DisplayLanguage;

/**
 * Class for adding elements such as buttons and Textfields to general layout of sprite creator 
 * 
 * @author taekwhunchung
 *
 */
public class SpriteCreatorGridManager extends SpriteObjectGridManager {

	private static final String TOOLSANDNAMES_PATH = "authoring/drawing/drawingTools/drawingTools";
	private static final String NAME_FIELD = "NameField";
	private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;
	private static final String PATH = "resources/";
	private static final String CATEGORY_FIELD = "CategoryField";
	private static final String DRAW_IMAGE = "Draw";
	private static final String CREATE_SPRITE = "CreateSprite";
	private static int ROWS = 3;
	private static int COLUMNS = 3;
	private BiFunction<Image, String, AbstractSpriteObject> getSpriteTypeFunction;
	private TextField nameField;
	private TextField categoryField;
	private AbstractSpriteObject newSprite;
	private AuthoringEnvironmentManager myAEM;
	private GameDataHandler GDH;
	private static ResourceBundle paintResources = ResourceBundle.getBundle(TOOLSANDNAMES_PATH);

	public SpriteCreatorGridManager(SpriteGridHandler SGH, GameDataHandler GDH) {
		super(ROWS, COLUMNS, SGH, GDH);
		this.GDH = GDH;
	}

	public SpriteCreatorGridManager() {
		super(ROWS, COLUMNS);
	}

	public SpriteCreatorGridManager(SpriteGridHandler SGH, GameDataHandler GDH, AuthoringEnvironmentManager AEM,
			BiFunction<Image, String, AbstractSpriteObject> getSpriteType) {
		this(SGH, GDH);

		myAEM = AEM;
	}

	public SpriteCreatorGridManager(AuthoringEnvironmentManager AEM,
			BiFunction<Image, String, AbstractSpriteObject> getSpriteType) {
		this();
		getSpriteTypeFunction = getSpriteType;
		myAEM = AEM;
	}

	protected SpriteCreatorGridManager(int rows, int columns, SpriteGridHandler SGH, GameDataHandler GDH) {
		super(rows, columns, SGH, GDH);
	}

	protected SpriteCreatorGridManager(int rows, int columns, SpriteGridHandler SGH, SpriteNameManager SNM,
			GameDataHandler GDH) {
		super(rows, columns, SGH, GDH);

	}

	@Override
	public void createMapLayer() {
		myMapLayer = new SpriteCreatorLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);

	}

	@Override
	public void setCanFillBackground() {
		canFillBackground = false;
	}

	public void setSpriteToCoverEntireGrid(AbstractSpriteObject ASO) {
		nameField.setText(ASO.getName());
		categoryField.setText("General");
		updateNewSpriteAndFillEntireGrid(() -> getMapLayer().setBackgroundImage(() -> ASO));
	}

	@Override
	public void getOnBackgroundChangeFunctionality(File file) {
		Image image = myAEM.getGameDataHandler().getImage(file);
		String fileName = file.getName();
		nameField.setText(file.getName());
		categoryField.setText("General");
		updateNewSpriteAndFillEntireGrid(
				() -> getMapLayer().setBackgroundImage(() -> getSpriteTypeFunction.apply(image, fileName)));
	}

	private void updateNewSpriteAndFillEntireGrid(Supplier<AbstractSpriteObject> fillGridSupplier) {
		newSprite = fillGridSupplier.get();
		this.populateCell(newSprite, new Integer[] { 0, 0 });
		this.mySpriteGridHandler.setActiveDisplayPanelSprite(newSprite);
	}

	private AbstractSpriteObject getNewSprite() {
		System.out.println("Getting sprite now");
		return newSprite;
	}

	public HBox getNameCategoryBox() {
		HBox nameCategoryBox = new HBox(10);
		nameCategoryBox.getChildren().addAll(addNameCategoryBox());
		return nameCategoryBox;
	}

	public HBox getSpriteButtonsBox() {
		HBox spriteButtonsBox = new HBox(10);
		spriteButtonsBox.getChildren().addAll(addButtons());
		return spriteButtonsBox;
	}

	private HBox addNameCategoryBox() {
		Label enterName = new Label();
		enterName.textProperty().bind(DisplayLanguage.createStringBinding(NAME_FIELD));
		nameField = new TextField();
		Label enterCategory = new Label();
		enterCategory.textProperty().bind(DisplayLanguage.createStringBinding(CATEGORY_FIELD));
		categoryField = new TextField();
		HBox nameCategoryBox = new HBox(10);
		nameCategoryBox.getChildren().addAll(enterName, nameField, enterCategory, categoryField);
		nameCategoryBox.setAlignment(Pos.CENTER);
		return nameCategoryBox;
	}

	private HBox addButtons() {

		Button createImageButton = new Button();
		createImageButton.textProperty().bind(DisplayLanguage.createStringBinding(DRAW_IMAGE));
		createImageButton.setOnAction(e -> {
			Stage newStage = new Stage();
			ImageCanvasPane paint = new ImageCanvasPane(500, 500, this::save);

			Scene paintScene = new Scene(paint);
			newStage.setScene(paintScene);
			newStage.show();
		});

		Button createSpriteButton = new Button();
		createSpriteButton.textProperty().bind(DisplayLanguage.createStringBinding(CREATE_SPRITE));

		createSpriteButton.setOnAction(e -> {
			AbstractSpriteObject dummySprite = getNewSprite();
			String spriteName = new String(nameField.getText());
			if (myAEM.getSpriteNameManager().isNameValidTemplate(spriteName)) {
				try {
					dummySprite.setName(spriteName);
					if (categoryField.getText() == null
							|| categoryField.getText().replaceAll("\\s+", "").length() == 0) {
						if (dummySprite instanceof SpriteObject) {
							myAEM.addUserSprite((SpriteObject) dummySprite);
						} else if (dummySprite instanceof InventoryObject) {
							myAEM.addInventorySprite((InventoryObject) dummySprite);
						}

					} else {
						if (dummySprite instanceof SpriteObject) {
							myAEM.addUserSprite(categoryField.getText(), (SpriteObject) dummySprite);
						} else if (dummySprite instanceof InventoryObject) {
							myAEM.addInventorySprite(categoryField.getText(), (InventoryObject) dummySprite);
						}
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				myAEM.getSpriteNameManager().addTemplateName(spriteName);

				nameField.clear();
				categoryField.clear();
				this.resetActiveCells();
				this.mySpriteGridHandler.deactivateActiveSprites();
				((AuthoringMapStackPane)dummySprite.getParent()).removeChild();

				newSprite = this.getSpriteTypeFunction.apply(null, null);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Name already exists");
				alert.setContentText("Change to a unique name");
				alert.showAndWait();
			}

		});
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(createImageButton, createSpriteButton);
		return buttonBox;
	}

	private void save(Image s) {
		final String fileName = ("UniqueSprite" + Math.random()).replaceAll("\\.", "") + ".png";
		System.out.println("fileName: " + fileName);
		myAEM.getGameDataHandler().saveTo(s, fileName);
		newSprite = getMapLayer().setBackgroundImage(() -> getSpriteTypeFunction.apply(s, fileName));
	}


}
