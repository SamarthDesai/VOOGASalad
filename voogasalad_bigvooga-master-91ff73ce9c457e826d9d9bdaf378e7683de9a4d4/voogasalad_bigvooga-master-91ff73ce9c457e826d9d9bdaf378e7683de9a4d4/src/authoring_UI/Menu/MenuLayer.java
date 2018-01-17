package authoring_UI.Menu;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import javafx.scene.paint.Color;

public class MenuLayer extends MapLayer {

	public MenuLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns,SGH, Color.TRANSPARENT);
	}

		public MenuLayer(int rows, int columns,  SpriteGridHandler SGH, Color c) {
		super(rows, columns, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("Menu View");
	}

}

