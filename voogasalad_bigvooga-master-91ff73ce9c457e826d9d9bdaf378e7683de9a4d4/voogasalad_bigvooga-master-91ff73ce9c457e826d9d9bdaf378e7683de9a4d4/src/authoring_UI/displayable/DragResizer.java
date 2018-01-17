package authoring_UI.displayable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * class that handles functionality for dragging + resizing a text area within bounded pane & deleting the TA upon right click. Used for dialogues + cutscenes
 * 
 * @author Dara Buggay
 *
 */

public class DragResizer {

    /**
     * The margin around the control that a user can click in to start resizing
     * the region.
     */
    private static final int RESIZE_MARGIN = 2;
    
	private static double orgSceneX, orgSceneY;
	private static double orgTranslateX, orgTranslateY;
	private static double paneLeft, paneRight, paneTop, paneBottom;

    private Region region;
    private Pane pane;
    private Rectangle bound;

    private double y, x;

    private boolean initMinHeight;

    private short dragging = 0;

    private final short NOTDRAGGING = 0;
    private final short NORTH = 1;
    private final short SOUTH = 2;
    private final short EAST = 3;
    private final short WEST = 4;

    
    protected DragResizer(Region aRegion) {
        region = aRegion;
        region.applyCss();
        // set clip bound
        pane = (Pane) aRegion.getParent();
        bound = new Rectangle(pane.getWidth(),pane.getHeight());
        pane.setClip(bound);
        
    }

    protected void makeTextAreaResizable() {
        final DragResizer resizer = new DragResizer(region);

        region.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            		resizer.mousePressed(event);
            }
        });
        region.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            		resizer.mouseDragged(event);
            }
        });
        region.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseOver(event);
            }
        });
        region.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseReleased(event);
            }
        });
    }

    protected void mouseReleased(MouseEvent event) {
        initMinHeight = false; //Reset each time
        dragging = NOTDRAGGING;
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event) {
        if (isInDraggableZoneS(event) || dragging == SOUTH) {
            region.setCursor(Cursor.S_RESIZE);
        } else if (isInDraggableZoneE(event) || dragging == EAST) {
            region.setCursor(Cursor.E_RESIZE);
        } else if (isInDraggableZoneN(event) || dragging == NORTH) {
            region.setCursor(Cursor.N_RESIZE);
        } else if (isInDraggableZoneW(event) || dragging == WEST) {
            region.setCursor(Cursor.W_RESIZE);
        } else {
            region.setCursor(Cursor.DEFAULT);
        }
    }

    private boolean isInDraggableZoneN(MouseEvent event) {
        return event.getY() < RESIZE_MARGIN;
    }

    private boolean isInDraggableZoneW(MouseEvent event) {
        return event.getX() < RESIZE_MARGIN;
    }

    private boolean isInDraggableZoneS(MouseEvent event) { return event.getY() > (region.getHeight() - RESIZE_MARGIN); }

    private boolean isInDraggableZoneE(MouseEvent event) { return event.getX() > (region.getWidth() - RESIZE_MARGIN); }


    private void mouseDragged(MouseEvent event) {
    	
    		if (dragging == SOUTH) {
    			if((region.getTranslateY() + region.getHeight()) < bound.getHeight()){
    				region.setMinHeight(event.getY());
  	         }
        } else if (dragging == EAST) {
        		if((region.getTranslateX() + region.getWidth()) < bound.getWidth()){
 		         region.setMinWidth(event.getX());
	         } 
        } else if (dragging == NORTH) {
            double prevMin = region.getMinHeight();
            if (region.getTranslateY() > 0) {
    				region.setMinHeight(region.getMinHeight() - event.getY());
    	            if (region.getMinHeight() < region.getPrefHeight()) {
    	                region.setMinHeight(region.getPrefHeight());
    	   		        region.setTranslateY(region.getTranslateY() - (region.getPrefHeight() - prevMin));
    	                return;
    	            }
    	            if (region.getMinHeight() > region.getPrefHeight() || event.getY() < 0)
    	            		region.setTranslateY(region.getTranslateY() + event.getY());
            }
            		
        } else if (dragging == WEST) {
            double prevMin = region.getMinWidth();
            if (region.getTranslateX() > 0) {
                region.setMinWidth(region.getMinWidth() - event.getX());
                if (region.getMinWidth() < region.getPrefWidth()) {
                    region.setMinWidth(region.getPrefWidth());
       		        region.setTranslateX(region.getTranslateX() - (region.getPrefWidth() - prevMin));
                    return;
                }
                if (region.getMinWidth() > region.getPrefWidth() || event.getX() < 0)
                		region.setTranslateX(region.getTranslateX() + event.getX());
            }
        }


    }

    private void mousePressed(MouseEvent event) {
        // ignore clicks outside of the draggable margin
        if (isInDraggableZoneE(event)) {
            dragging = EAST;
        } else if (isInDraggableZoneS(event)) {
            dragging = SOUTH;
        } else if (isInDraggableZoneN(event)) {
            dragging = NORTH;
        } else if (isInDraggableZoneW(event)) {
            dragging = WEST;
        } else
            return;


        // make sure that the minimum height is set to the current height once,
        // setting a min height that is smaller than the current height will
        // have no effect
        if (!initMinHeight) {
            region.setMinHeight(region.getHeight());
            region.setMinWidth(region.getWidth());
            initMinHeight = true;
        }

    }
    
    protected void makeTextAreaDraggable() {
    		Node textAreaContent = region.lookup(".content");

     	textAreaContent.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

     		orgSceneX = e.getSceneX();
     		orgSceneY = e.getSceneY();
     		orgTranslateX = region.getTranslateX();
     		orgTranslateY = region.getTranslateY();

     		region.toFront();
     		textAreaContent.setCursor(Cursor.MOVE);

     	});

     	textAreaContent.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
      		double offsetX = e.getSceneX() - orgSceneX;
      		double offsetY = e.getSceneY() - orgSceneY;
      		double newTranslateX = orgTranslateX + offsetX;
      		double newTranslateY = orgTranslateY + offsetY;

	        if(newTranslateX > 0 &&
	        		(newTranslateX + region.getWidth()) < bound.getWidth()){
	             region.setTranslateX(newTranslateX);
	        	}
	        if(newTranslateY > 0 &&
	            (newTranslateY + region.getHeight()) < bound.getHeight()){
	            region.setTranslateY(newTranslateY);
	        }

	        textAreaContent.setCursor(Cursor.HAND);

     	});
      
     	textAreaContent.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            textAreaContent.setCursor(Cursor.HAND);
          });
           
    }
    
	protected void makeTextAreaDeletable() {
		Node textAreaContent = region.lookup(".content");
		
		textAreaContent.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			MouseButton button = e.getButton();
			if (button == MouseButton.SECONDARY) {
				pane.getChildren().remove(region);
			}

		});
	}
    
}
