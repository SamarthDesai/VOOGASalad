package authoring_UI.HUD;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import javafx.scene.paint.Color;

public class HUDLayer extends MapLayer {

	public HUDLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, SGH, Color.TRANSPARENT);
	}

		public HUDLayer(int rows, int columns, SpriteGridHandler SGH, Color c) {
		super(rows, columns, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("HUD View");
	}

}
