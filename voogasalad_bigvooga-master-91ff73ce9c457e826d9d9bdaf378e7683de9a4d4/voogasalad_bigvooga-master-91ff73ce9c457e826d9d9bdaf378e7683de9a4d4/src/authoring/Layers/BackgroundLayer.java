package authoring.Layers;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.AuthoringMapStackPane;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BackgroundLayer extends MapLayer {
	
	public BackgroundLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, SGH, Color.TRANSPARENT);
	}
	
	public BackgroundLayer(int rows, int columns, SpriteGridHandler SGH, AbstractSpriteObject backgroundSpriteObject) {
		super(rows, columns, SGH, Color.TRANSPARENT);
//		this.setGridLinesVisible(false);
//		setDefaultColor(Color.YELLOW);
		setName("Background");
		setBackgroundImage(()->backgroundSpriteObject);
	}

	BackgroundLayer(int rows, int columns, SpriteGridHandler SGH, Color c) {
		super(rows, columns, SGH, c);
//		this.setGridLinesVisible(false);
//		setDefaultColor(Color.YELLOW);
		setName("Background");
	}
	
	@Override 
	public AbstractSpriteObject setBackgroundImage(Supplier<AbstractSpriteObject> suppliedASO){
		AuthoringMapStackPane AMSP = this.getChildAtPosition(0, 0);
		if (AMSP.hasChild()){
			AMSP.removeChild();
		}
		this.getChildren().forEach(cell->{
			((AuthoringMapStackPane) cell).setInactiveBackground(Color.TRANSPARENT);
		});

		AbstractSpriteObject ASO = suppliedASO.get();
		ASO.setNumCellsHeightNoException(this.numRowsProperty.get());
		ASO.setNumCellsWidthNoException(this.numColumnsProperty.get());
		AMSP.addChild(ASO);
//		AMSP.setRowSpan(this.numRowsProperty.get());
//		AMSP.setColSpan(this.numColumnsProperty.get());
		numColumnsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			AMSP.setColSpan(newNumColumns);
		});
		numRowsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			AMSP.setRowSpan(newNumColumns);
		});
		ASO.getHeightObjectProperty().addListener((change, oldVal, newVal)->{
			numRowsProperty.set(newVal);
		});
		
		ASO.getWidthObjectProperty().addListener((change, oldVal, newVal)->{
			numColumnsProperty.set(newVal);
		});
		return ASO;
	}
	
	

}
