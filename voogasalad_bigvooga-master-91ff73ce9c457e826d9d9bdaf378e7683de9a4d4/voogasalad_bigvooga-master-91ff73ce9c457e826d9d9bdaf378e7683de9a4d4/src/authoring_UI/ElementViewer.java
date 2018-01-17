package authoring_UI;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class where users can see all saved objects
 * 
 * @author taekwhunchung
 *
 */

public class ElementViewer {

	private Stage myStage;
	private Scene myScene;
	private TabPane myTabPane;
	// private GridPane myGrid;
	// private CheckBox myCB;
	// private ArrayList<String> mySelection;
	private ArrayList<String> myObjList;
	private ArrayList<String> myOptionList;

	public static final double SCENE_WIDTH = 790;
	public static final double SCENE_HEIGHT = 600;

	public ElementViewer() {
		myStage = new Stage();
		myTabPane = new TabPane();

		myObjList = new ArrayList<String>();
		myObjList.add("sprites");
		myObjList.add("dialogues");
		myObjList.add("cut scenes");

		myOptionList = new ArrayList<String>();
		myOptionList.add("default");
		myOptionList.add("user");
		myOptionList.add("imported");

		createTopTabs();

		TabPane test = new TabPane();
		Tab tab_2 = new Tab();
		tab_2.setText("test_2");
		test.getTabs().add(tab_2);
		test.setSide(Side.RIGHT);

		GridPane gp = new GridPane();

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				gp.add(sp, i, j);
			}
		}

		ScrollPane sp = new ScrollPane(gp);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		tab_2.setContent(sp);

		// gp.setPrefHeight(200);
		// gp.setPrefWidth(200);
		// sp.setPrefWidth(200);
		// sp.setPrefHeight(100);
		//

		// sp.setContent(gp);
		// tab_2.setContent(sp);

		myScene = new Scene(myTabPane, SCENE_WIDTH, SCENE_HEIGHT);
		myStage.setScene(myScene);

		// myGrid = new GridPane();
		//
		// mySelection = new ArrayList<String>();
		// mySelection.add("maps");
		// mySelection.add("sprite objects");
		// mySelection.add("inventory objects");
		//
		// ColumnConstraints col1 = new ColumnConstraints();
		// col1.setPercentWidth(50);
		// ColumnConstraints col2 = new ColumnConstraints();
		// col2.setPercentWidth(50);
		//
		// RowConstraints row1 = new RowConstraints();
		// row1.setPercentHeight(33);
		// RowConstraints row2 = new RowConstraints();
		// row2.setPercentHeight(33);
		// RowConstraints row3 = new RowConstraints();
		// row3.setPercentHeight(34);
		//
		// myGrid.getColumnConstraints().addAll(col1, col2);
		// myGrid.getRowConstraints().addAll(row1, row2, row3);
		//
		// // myGrid.setGridLinesVisible(true);
		// myOM = new OverviewManager();
		//
		// myOM.makeOverviewButtons(myGrid);
		//
		// myGrid.add(myOM.getNode(), 1, 1);

		myStage.show();
	}

	private void createTopTabs() {
		for (String s : myObjList) {
			Tab tab = new Tab();
			tab.setText(s);
			myTabPane.getTabs().add(tab);
			myTabPane.setSide(Side.TOP);

			TabPane innerTP = new TabPane();
			for (String o : myOptionList) {
				Tab innerTab = new Tab();
				innerTab.setText(o);
				innerTP.getTabs().add(innerTab);
				innerTP.setSide(Side.RIGHT);
				tab.setContent(innerTP);

				GridPane gp = new GridPane();

				for (int i = 0; i < 15; i++) {
					for (int j = 0; j < 15; j++) {
						StackPane sp = new StackPane();
						sp.setPrefHeight(50);
						sp.setPrefWidth(50);
						sp.setBackground(
								new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
						BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED,
								CornerRadii.EMPTY, BorderWidths.DEFAULT);
						sp.setBorder(new Border(border));
						gp.add(sp, i, j);
					}
				}

				ScrollPane mySP = new ScrollPane(gp);

				innerTab.setContent(mySP);
			}

		}

	}

	//
	// private void makeButtons() {
	// for (int i = 0; i < mySelection.size(); i++) {
	// myGrid.add(newButton(mySelection.get(i)), 0, i);
	// }
	// }
	//
	// private Node newButton(String s) {
	// Button button = new Button(s);
	// button.setOnAction(e -> {
	//
	// });
	// return button;
	// }
	//
	// private void makeCheckBoxes() {
	// for (int i = 0; i < mySelection.size(); i++) {
	// myGrid.add(newCheckBox(mySelection.get(i)), 0, i);
	// }
	// }
	//
	// private Node newCheckBox(String s) {
	// CheckBox cb = new CheckBox(s);
	// return cb;
	// }
	//

}
