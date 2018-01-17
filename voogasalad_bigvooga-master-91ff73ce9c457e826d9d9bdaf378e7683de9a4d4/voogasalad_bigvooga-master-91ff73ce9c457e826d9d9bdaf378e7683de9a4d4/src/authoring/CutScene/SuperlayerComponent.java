package authoring.CutScene;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class SuperlayerComponent {
	
protected String myColor;
	
protected double relativeYCoor;
protected double relativeXCoor;
protected double relativeHeight;
protected double relativeWidth;
	
protected double absoluteWidth;
protected double absoluteHeight;
protected double absoluteCenterX;
protected double absoluteCenterY;
	
protected double paneHeight;
protected double paneWidth;
	
	public SuperlayerComponent(Pane parent, Node child){
		paneHeight = parent.getHeight();
		paneWidth=parent.getWidth();
		
		setColorFromCSS(child.getStyle());
		
		this.setAbsoluteDimensions(this.getBoundsInParent(parent, child));
		this.setRelativeYCoor(this.findRelativeYCoor());
		this.setRelativeXCoor(this.findRelativeXCoor());
		this.setRelativeHeight(this.findRelativeHeight());
		this.setRelativeWidth(this.findRelativeWidth());
	}
	
	public SuperlayerComponent(){
		
	}
	
	
	protected void setColorFromCSS(String cssRepresentation){
		myColor = "#000000";
		return;
	}
	
	 public void setColor(String newColor){
	    	myColor = newColor;
	    }
	    
	    public String getColor(){
	    	System.out.println("Returning from getColor: "+ this.myColor);
	    	return this.myColor;
	    }
	    
		
	    protected Bounds getBoundsInParent(Pane parent, Node child){
			return child.getBoundsInParent();
		}
		
	    protected void setAbsoluteDimensions(Bounds boundsInParent){
			absoluteWidth = boundsInParent.getWidth();
			absoluteHeight = boundsInParent.getHeight();
			absoluteCenterX = boundsInParent.getMinX()+absoluteWidth/2;
			absoluteCenterY = boundsInParent.getMinY()+absoluteHeight/2;
		}
		

		public double getRelativeYCoor() {
			return relativeYCoor;
		}

		public void setRelativeYCoor(double relativeYCoor) {
			this.relativeYCoor = relativeYCoor;
		}

		public double getRelativeXCoor() {
			return relativeXCoor;
		}

		public void setRelativeXCoor(double relativeXCoor) {
			this.relativeXCoor = relativeXCoor;
		}

		public double getRelativeHeight() {
			return relativeHeight;
		}

		public void setRelativeHeight(double relativeHeight) {
			this.relativeHeight = relativeHeight;
		}

		public double getRelativeWidth() {
			return relativeWidth;
		}

		public void setRelativeWidth(double relativeWidth) {
			this.relativeWidth = relativeWidth;
		}
		
			
		
		protected double findRelativeYCoor() {
			double pos = this.absoluteCenterY/paneHeight;
			return pos-.5;
		}

		protected double findRelativeXCoor() {
			double pos = this.absoluteCenterX/paneWidth;
			return pos-.5;
		}

		protected double findRelativeHeight() {
			double height = this.absoluteHeight/paneHeight;
			return height;
		}

		protected double findRelativeWidth() {
			double width = this.absoluteWidth/paneWidth;
			return width;
		}
		
		public abstract SuperlayerComponent clone();
	
	

}
