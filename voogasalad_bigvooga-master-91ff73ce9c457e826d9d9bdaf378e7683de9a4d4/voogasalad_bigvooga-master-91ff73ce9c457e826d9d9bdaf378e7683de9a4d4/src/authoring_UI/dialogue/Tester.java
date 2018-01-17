package authoring_UI.dialogue;

import authoring.Sprite.AbstractSpriteObject;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * class used to test dragging and resizing text areas and images before adding them to dialogues + cutscenes
 * 
 * @author Dara Buggay
 *
 */

public class Tester extends Application {
	
	private static double orgSceneX, orgSceneY;
	private static double orgTranslateX, orgTranslateY;

	@Override
	public void start(Stage stage){
	    stage.setTitle("BPM");
	    BorderPane mainPanel = new BorderPane();

	    VBox nameList = new VBox();
	    nameList.getChildren().add(new Label("Data"));
	    nameList.setPrefWidth(150);

	    Pane canvas = new Pane();
	    canvas.setStyle("-fx-background-color: #ffe3c3;");
	    canvas.setPrefSize(400,300);

	    Circle anchor = new Circle(10);

	    double rectWidth = 50, rectHeight = 50;
	    TextArea ta = new TextArea();
	    ta.setPrefSize(50, 50);
       	ta.applyCss();


	    
	    canvas.getChildren().addAll(ta, anchor);
       	

	    // set the clip boundary
	    Rectangle bound = new Rectangle(canvas.getPrefWidth(),canvas.getPrefHeight());
	    canvas.setClip(bound);

	   

	    mainPanel.setLeft(nameList);
	    mainPanel.setCenter(canvas);
	    Scene scene = new Scene(mainPanel, 800, 600);
	    stage.setScene(scene);
	    stage.show();
	    
	    Node rect = ta.lookup(".content");

      rect.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

        	orgSceneX = e.getSceneX();
        	orgSceneY = e.getSceneY();
        	orgTranslateX = ta.getTranslateX();
        	orgTranslateY = ta.getTranslateY();

        	ta.toFront();
        ta.setCursor(Cursor.MOVE);

      });

      rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {


      		double offsetX = e.getSceneX() - orgSceneX;
      		double offsetY = e.getSceneY() - orgSceneY;
      		double newTranslateX = orgTranslateX + offsetX;
      		double newTranslateY = orgTranslateY + offsetY;
      		Point2D currentPointer = new Point2D(e.getX(), e.getY());
	        if(bound.getBoundsInLocal().contains(currentPointer)){

	            if(newTranslateX > 0 &&
	                    (newTranslateX + ta.getWidth()) < bound.getWidth()){
	                ta.setTranslateX(newTranslateX);
	            }
	            if(newTranslateY > 0 &&
	                    (newTranslateY + ta.getHeight()) < bound.getHeight()){
	                ta.setTranslateY(newTranslateY);
	            }
	        }
      });
      rect.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            rect.setCursor(Cursor.HAND);
          }
       );

	}
}
