package authoring.Sprite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.Sprite.Parameters.FEParameter;
import authoring.Sprite.Parameters.FEParameterFactory;
import authoring.Sprite.Parameters.SpriteParameter;
import authoring.Sprite.Parameters.SpriteParameterFactory;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring_UI.TabContentVBox;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpriteParameterTabsAndInfo {

	private TabPane myTabPane;
	private int categoryCounter = 1;
	private Map<String, Integer> counters;
	private AbstractSpriteObject mySO;
	private Map<String, String> catNames;
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab currentTab;

	public SpriteParameterTabsAndInfo() {
		this(new SpriteObject(false));
	}

	SpriteParameterTabsAndInfo(AbstractSpriteObject SO) {
		setupCounters();
		setupCategoryNamesMap();
		createCategoryTabPane();
		createAddCategoryTab();
		setSpriteObject(SO);
	}

	private void setupCounters() {
		counters = new HashMap<String, Integer>();
		counters.put("String", 1);
		counters.put("Double", 1);
		counters.put("Boolean", 1);
	}

	private void setupCategoryNamesMap() {
		catNames = new HashMap<String, String>();
	}

	public void setSpriteObject(AbstractSpriteObject SO) {
		mySO = SO;
		create();
	}

	public ObservableList<Tab> getTabs() {
		return myTabPane.getTabs();
	}

	public TabPane getTabPane() {
		return myTabPane;
	}

	public void clearTabPane() {
		myTabPane.getTabs().clear();
		int numTabs = myTabPane.getTabs().size();
		myTabPane.getTabs().remove(0, numTabs);
		this.catNames.clear();
//		;
		// .clear();
	}

	public TabPane create() {
		clearTabPane();
		// createAddCategoryTab();
		createFromSO(mySO);
//		;
		this.createAddCategoryTab();
		return getTabPane();
	}

	public void create(AbstractSpriteObject SO) {
		setSpriteObject(SO);
		// return create();
	}

	public void createFromSO(AbstractSpriteObject SO) {
		categoryCounter = 1;
		this.catNames.clear();
		;
		Map<String, List<SpriteParameter>> params = SO.getParameters();
//		;
		boolean loopedOnce = false;
		for (Map.Entry<String, List<SpriteParameter>> entry : params.entrySet()) {
//			;

			String category = entry.getKey();
//			;
			// this.catNames.put(category, category);
			List<SpriteParameter> newParams = entry.getValue();
//			;
			FEParameterFactory newFactory = new FEParameterFactory(newParams);
//			;

			addCategoryTab(category, newFactory);
			loopedOnce = true;
		}
		if (!loopedOnce) {
			this.addEmptyCategoryTab();
		}
	}

	public void createCategoryTabPane() {
		myTabPane = new TabPane();
		myTabPane.setSide(Side.RIGHT);
		myTabPane.setPrefHeight(500);
		myTabPane.setPrefWidth(400);
		mySelectModel = myTabPane.getSelectionModel();
	}

	private void addEmptyCategoryTab() {
		// String category = String.format("Category%d", categoryCounter++);
		String category = "Category1";
		currentTab = new Tab();
		currentTab.setContent(createEmptyCategoryVBox(category, currentTab));
//		;
		currentTab.setClosable(false);
		int numtabs = myTabPane.getTabs().size()-1;
		// numtabs-1 to make sure the 'add tab' tab is the last tab
//		;
		numtabs = numtabs<0 ? 0 : numtabs;
		myTabPane.getTabs().add(numtabs, currentTab);
		mySelectModel.select(currentTab);
//		;
	}

	private void addCategoryTab(String category, VBox vbox) {
		Tab newCategory = new Tab(category);
		newCategory.setContent(createCategoryVBox(category, vbox, newCategory));
		newCategory.setClosable(false);
		int index = myTabPane.getTabs().size()-1;
		index = (index<0) ? 0 : index;
		myTabPane.getTabs().add(index, newCategory);
	}

	private ScrollPane createStatePane(VBox temp) {
//		;
		ScrollPane myStateSP_dummy = createEmptyScrollPane();
		formatParametersVBox(temp);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
	}

	private VBox createCategoryVBox(String category, VBox vbox, Tab parentTab) {
		TabContentVBox emptyVBox = new TabContentVBox(parentTab);
		emptyVBox.getChildren().addAll(createCategoryName(category, parentTab), createStatePane(vbox),
				createButtonsHBox(emptyVBox));
		return emptyVBox;
	}

	private HBox createCategoryName(String category, Tab parentTab) {
		HBox catNameHbox = new HBox(5);
		Label cat = new Label ("Category Name");
		TextField catName = new TextField("");
		int num = 1;
//		;
//		;
		while (catNames.containsValue(category)) {
//			;
			category = String.format("Category%d", num++);

		}
//		;
		catName.setText(category);
		catNames.put(category, category);
		parentTab.setText(category);

		catName.textProperty().addListener((observable, prev, next) -> {
			String newText = next;
//			;
			if (catNames.containsValue(next)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("HEADER");
				alert.setContentText("That category name already used: " + next);
				alert.showAndWait();

				// newText
				// catName.setText("");
			} else {
				// if (catNames.containsValue(prev)){
				// catNames.containsValue(prev)
				for (String key : catNames.keySet()) {
					if (catNames.get(key).equals(prev)) {
						catNames.put(key, next);
					}
				}
				// } else {
				// catNames.put(prev, next);
				// }

				// mySO.changeCategoryName(prev, next);
			}
			parentTab.setText(newText);
			catName.setText(newText);
		});

		catNameHbox.getChildren().addAll(cat, catName);
		catNameHbox.setAlignment(Pos.CENTER);
		return catNameHbox;
	}

	private VBox createEmptyCategoryVBox(String category, Tab parentTab) {
		VBox vbox = new VBox();
		return createCategoryVBox(category, vbox, parentTab);
	}

	private Button createAddParameterButton(String type) {
		Button addParam = new Button(String.format("Add %s Parameter", type));
		return addParam;
	}

	private ScrollPane createEmptyScrollPane() {
		VBox vbox = new VBox();
		formatParametersVBox(vbox);
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setContent(vbox);
		return myStateSP_dummy;
	}

	private void setButtonAction(Button button, TabContentVBox vbox, String type, Object varValue) {
		button.setOnAction(e -> {
			ScrollPane SP = null;
			for (Node node : vbox.getChildren()) {
				if (node instanceof ScrollPane) {
					SP = (ScrollPane) node;
				}
			}
			if (SP == null) {
				throw new RuntimeException();
			}
			String category = vbox.getCategory();
//			String category =
			VBox SP_Content = (VBox) SP.getContent();

			Integer varCount = counters.get(type);
			varCount++;
			counters.put(type, varCount);

			String varName = String.format("%sParameter%d", type, varCount);

			SpriteParameter SPI = SpriteParameterFactory.makeParameter(varName, varValue);
			// mySO.addParameter(category, SPI);

			mySO.addPossibleParameter(category, SPI);
			SP_Content.getChildren().add(FEParameterFactory.makeFEParameter(SPI));
		});

	}

	private Button createAddStringParameterButton(TabContentVBox vbox) {
		String type = "String";
		String val = "";
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}

	private Button createAddDoubleParameterButton(TabContentVBox vbox) {
		String type = "Double";
		Double val = 0.0;
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}

	private Button createAddBooleanParameterButton(TabContentVBox vbox) {
		String type = "Boolean";
		Boolean val = false;
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}

	private HBox createButtonsHBox(TabContentVBox vbox) {
		HBox buttonsHBox = new HBox();
		buttonsHBox.getChildren().addAll(createAddStringParameterButton(vbox), createAddDoubleParameterButton(vbox),
				createAddBooleanParameterButton(vbox));
		return buttonsHBox;
	}

	private void formatParametersVBox(VBox in) {
		in.setPrefHeight(200);
	}

	private void createAddCategoryTab() {
		Tab newCategory = new Tab();
		newCategory.setText("Add Category");
		newCategory.setClosable(false);
		myTabPane.getTabs().add(newCategory);
		for (Tab t : myTabPane.getTabs()) {
//			;
		}
//		;
		newCategory.setOnSelectionChanged(e -> {
//			;
			addEmptyCategoryTab();
		});

	}

	public void apply() {
		int categoriesAdded = mySO.acceptPossibleParameters();
		categoryCounter -= categoriesAdded;
		ObservableList<Tab> tabs = myTabPane.getTabs();
//		;
		for (int i = 0; i < tabs.size() - 1; i++) {
//			;
			Tab t = tabs.get(i);
//			;
			TabContentVBox TCV = (TabContentVBox) t.getContent();
			// String catName = TCV.getMyCategory();
			String catName = t.getText();
//			;

			if (catNames.containsValue(catName)) {
				for (String key : catNames.keySet()) {
					if (catNames.get(key).equals(catName)) {
						mySO.updateCategoryName(key, catName);
					}
				}
			}

			// if (catNames.containsKey(catName)){
			//// categoryCounter--;
			// String newCatName = catNames.get(catName);
			//// ;
			//// t.setText(newCatName);
			// mySO.updateCategoryName(catName, newCatName);
			// }
			//

			ScrollPane SP = null;
			for (Node node : TCV.getChildren()) {
				if (node instanceof ScrollPane) {
//					;
					SP = (ScrollPane) node;
				}

			}
			VBox paramsVbox = (VBox) SP.getContent();
			int num = 0;
			for (Node node : paramsVbox.getChildren()) {
//				;
				FEParameter FEP = (FEParameter) node;
//				;
				FEP.updateParameter();
			}

		}
		System.out.println("ParamterTab applying to sprite: "+mySO);
		mySO.clearPossibleParameters();

	}

}
