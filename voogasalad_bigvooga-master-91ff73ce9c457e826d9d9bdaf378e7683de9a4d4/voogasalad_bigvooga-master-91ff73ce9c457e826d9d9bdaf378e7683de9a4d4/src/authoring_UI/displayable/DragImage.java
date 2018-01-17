package authoring_UI.displayable;

import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * class that handles functionality for dragging an image within a bounded pane & deleting an image upon right click. Used for dialogues + cutscenes
 * 
 * @author Dara Buggay
 *
 */
public class DragImage {
	
	private ImageView myImage;
	private Pane pane;
	private Rectangle bound;
	
	private static double orgSceneX, orgSceneY;
	private static double orgTranslateX, orgTranslateY;

	protected DragImage(ImageView image) {
		myImage = image;
		myImage.applyCss();
		
		pane = (Pane) image.getParent();
	    bound = new Rectangle(pane.getWidth(),pane.getHeight());
	    pane.setClip(bound);
	
	}
	
	protected void makeImageDraggable() {

		myImage.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = myImage.getTranslateX();
			orgTranslateY = myImage.getTranslateY();
			myImage.setCursor(Cursor.HAND);
			
		});

		myImage.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
  		double offsetX = e.getSceneX() - orgSceneX;
  		double offsetY = e.getSceneY() - orgSceneY;
  		double newTranslateX = orgTranslateX + offsetX;
  		double newTranslateY = orgTranslateY + offsetY;

        if(newTranslateX > 0 &&
        		(newTranslateX + myImage.getFitWidth()) < bound.getWidth()){
        		myImage.setTranslateX(newTranslateX);
        	}
        if(newTranslateY > 0 &&
            (newTranslateY + myImage.getFitHeight()) < bound.getHeight()){
        		myImage.setTranslateY(newTranslateY);
        }

        myImage.setCursor(Cursor.HAND);

		});
  
		myImage.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			myImage.setCursor(Cursor.HAND);
		});    

	}
	
	protected void makeImageDeletable() {
		myImage.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			MouseButton button = e.getButton();
			if (button == MouseButton.SECONDARY)
			pane.getChildren().remove(myImage);

		});
	}

}
