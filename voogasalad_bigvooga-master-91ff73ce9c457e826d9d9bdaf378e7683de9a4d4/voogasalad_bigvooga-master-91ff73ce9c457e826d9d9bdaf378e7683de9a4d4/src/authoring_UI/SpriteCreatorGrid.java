package authoring_UI;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Insets;
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

public class SpriteCreatorGrid extends GridPane {


	protected SpriteCreatorGrid(List<AbstractSpriteObject> sprites) {

		int totalRows = (int) Math.ceil(sprites.size() / 10);
		int DEFAULT_MIN_ROWS = 15;
		totalRows = (totalRows < DEFAULT_MIN_ROWS) ? DEFAULT_MIN_ROWS : totalRows;
		int counter = 0;
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < 10; j++) {
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));

				if (counter < sprites.size()) {
					AbstractSpriteObject toLoad = sprites.get(counter);
					toLoad.setOnMouseClicked(e -> {
						;
						// myDP.addSpriteEditorVBox();
						// myDP.updateParameterTab();
					});
					sp.getChildren().add(toLoad);
				}
				counter++;

				this.add(sp, j, i);
			}
		}
		
		this.setMaxHeight(WelcomeScreen.HEIGHT / 2);

	}
}
