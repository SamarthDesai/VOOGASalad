package authoring;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Thumbnail extends HBox{
	
	private int HEIGHT = 40;
	private int WIDTH = 100;
	private boolean isClicked;
	protected Runnable onButtonClick;
	protected Button sideButton;
	
	public Thumbnail(ImageView im, String name){
		setup(im, name);
	}
	
	public Thumbnail(ImageView im, String name, Boolean showSideButton){ 
		this(im, name);
		if (showSideButton){
			this.addSideButton();
		}
	}
	
	private void createLabel(String name){
		Label label = new Label(name);
		label.setAlignment(Pos.CENTER);
		this.getChildren().add(label);
	}
	
	private void createImageThumbnail(ImageView im){
		int thumbnail_size = (int) Math.floor(HEIGHT*.8);
		ImageView imView = new ImageView(im.snapshot(null, null));
//		imView.setPreserveRatio(true);
		imView.setFitHeight(thumbnail_size);
		imView.setFitWidth(thumbnail_size);
//		imView.wid
		
		this.getChildren().add(imView);
		this.setSpacing(30);
	}
	
	public void isClicked(boolean in){
		isClicked = in;
		if (in){
			this.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
//			this.setOpacity(50);
		} else {
			this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
//			this.setOpacity(100);
		}
	}
	
	public boolean isClicked(){
		return isClicked;
	}
	
	private void setup(ImageView im, String name){
		this.setSpacing(30);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		this.setAlignment(Pos.CENTER);
		createImageThumbnail(im);
		createLabel(name);
		onButtonClick = new Runnable(){
			@Override
			public void run() {
				// Do nothing by default		
			}	
		};
	}
	
	protected void addSideButton(){
		addSideButton("");
	}
	
	public void addSideButton(String text){
		sideButton = new Button();
		sideButton.setText(text);
		sideButton.setOnAction(event->{
			this.onButtonClick.run();
		});
		this.getChildren().add(sideButton);
	}
	
	public void addSideButton(String text, Runnable r){
		sideButton = new Button();
		sideButton.setText(text);
		setSideButtonRunnable(r);
		sideButton.setOnAction(event->{
			r.run();
		});
		this.getChildren().add(sideButton);
	}
	
	public void removeSideButton(){
		this.getChildren().remove(sideButton);
	}

	public void setSideButtonRunnable(Runnable r){
		this.onButtonClick = r;
	}

}
