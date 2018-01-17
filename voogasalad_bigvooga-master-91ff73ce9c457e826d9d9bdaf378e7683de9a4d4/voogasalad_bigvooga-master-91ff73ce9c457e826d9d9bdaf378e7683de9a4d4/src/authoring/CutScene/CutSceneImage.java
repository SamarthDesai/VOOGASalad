package authoring.CutScene;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CutSceneImage extends SuperlayerComponent{

	public CutSceneImage(Pane parent, ImageView child){
		super(parent, child);
	}
	
	public CutSceneImage(){
		super();
	}

	public CutSceneImage clone(){
		CutSceneImage ret = new CutSceneImage();
		ret.setColor(this.getColor());
		ret.setRelativeHeight(this.getRelativeHeight());
		ret.setRelativeWidth(this.getRelativeWidth());
		ret.setRelativeXCoor(this.getRelativeXCoor());
		ret.setRelativeYCoor(this.getRelativeYCoor());
		return ret;
	}
}
