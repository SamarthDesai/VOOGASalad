package authoring.DialogSprite;

import java.util.ArrayList;
import java.util.List;

import authoring.CutScene.CutSceneImage;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SuperlayerSprite {

	private String myFileURL;
	
	private List<DialogText> myTexts;
	private List<CutSceneImage> myCSImages;
	private int cellsWidth = 4;
	private int cellsHeight  = 2;

	public SuperlayerSprite(Pane pane, String fileURL) {
		myFileURL = fileURL;
		myTexts = new ArrayList<DialogText>();
		myCSImages = new ArrayList<CutSceneImage>();
		pane.getChildren().forEach((child) -> {
			addSuperlayerComponent(pane, child);
		});
		
	}
	
	public SuperlayerSprite(Pane pane, String fileURL, String type) {
		this(pane, fileURL);
		switch (type){
		case "Dialog":
			cellsWidth = 4;
			cellsHeight = 2;
			break;
		case "Cutscene":
			cellsWidth = 10;
			cellsHeight = 10;
			break;
		default:
			cellsWidth = 10;
			cellsHeight = 10;
			break;
		}
	}
	
	public SuperlayerSprite(){
		
	}
	
	private void addSuperlayerComponent(Pane pane, Node child){
		if (child instanceof ImageView){
			myCSImages.add(createCutSceneImage(pane, (ImageView)child));
		} else if (child instanceof TextArea){
			myTexts.add(createDialogText(pane, (TextArea)child));
		} else {
			//Do nothing
		}
	}

	private DialogText createDialogText(Pane parent, TextArea ta) {
		DialogText ret = new DialogText(parent, ta);
		return ret;
	}
	
	private CutSceneImage createCutSceneImage(Pane parent, ImageView iv) {
		CutSceneImage ret = new CutSceneImage(parent, iv);
		return ret;
	}
	
	public List<DialogText> getDialogText(){
		return myTexts;
	}
	public void setDialogText(List<DialogText> dialogTexts){
		myTexts = dialogTexts;
	}
	
	public List<CutSceneImage> getCutSceneImage(){
		return myCSImages;
	}
	public void setCutSceneImage(List<CutSceneImage> cutSceneImages){
		myCSImages = cutSceneImages;
	}
	
	
	public String getImageFileURL(){
		return this.myFileURL;
	}
	
	public void setImageFileURL(String newURL){
		myFileURL = newURL;
	}
	
	public int getCellsWidth(){
		return cellsWidth;
	}
	
	public int getCellsHeight(){
		return cellsHeight;
	}
	
	
	/**
	 * https://stackoverflow.com/questions/20357542/changing-text-area-string-colors-in-javafx-using-the-colorpicker
	 * @param c 
	 * @return String RGB representation
	 */
	private String toRgbString(Color c) {
        return "rgb("
                          + to255Int(c.getRed())
                    + "," + to255Int(c.getGreen())
                    + "," + to255Int(c.getBlue())
             + ")";
    }

    /**
     * https://stackoverflow.com/questions/20357542/changing-text-area-string-colors-in-javafx-using-the-colorpicker
     * @param d
     * @return int representation of color channel
     */
    private int to255Int(double d) {
        return (int) (d * 255);
    }
    
    public SuperlayerSprite clone(){
    	SuperlayerSprite ret = new SuperlayerSprite();
    	ret.setImageFileURL(new String(this.getImageFileURL()));
    	List<DialogText> newTexts = new ArrayList<DialogText>();
    	this.getDialogText().forEach(dialog->{
    		newTexts.add(dialog.clone());
    	});
    	ret.setDialogText(newTexts);
    	List<CutSceneImage> newCSImages = new ArrayList<CutSceneImage>();
    	this.getCutSceneImage().forEach(csimage->{
    		newCSImages.add(csimage.clone());
    	});
    	ret.setCutSceneImage(newCSImages);
    	return ret;
    }

	

}
