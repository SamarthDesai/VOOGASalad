package authoring_UI.Map;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.AuthoringMapStackPane;
import authoring_UI.SpriteGridHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class MapLayer extends GridPane {

	private int myRows;
	private int myColumns;
	protected SpriteGridHandler mySGH;
	private Color defaultColor;
	protected Color fillEmptyCellColor;
	private String myName;
	public static final int CELL_SIZE = 50;
	protected ObjectProperty<Integer> numRowsProperty;
	protected ObjectProperty<Integer> numColumnsProperty;
	private Set<AuthoringMapStackPane> activeGridCells;
	private Set<AuthoringMapStackPane> mostRecentActive;

	protected MapLayer(int rows, int columns, SpriteGridHandler SGH, Color c) {
		super();
		this.visibleProperty().addListener((change, previous, next)->{
			if (!next){
				this.removeAllActive();
			}
		});
		
		defaultColor = c;
		activeGridCells = new HashSet<AuthoringMapStackPane>();
		mostRecentActive = new HashSet<AuthoringMapStackPane>();
		numRowsProperty = new SimpleObjectProperty<Integer>();
		numColumnsProperty = new SimpleObjectProperty<Integer>();
		numRowsProperty.set(1);
		numColumnsProperty.set(1);
		numRowsProperty.addListener((observable, oldNumRows, newNumRows)->{
			Integer diff = newNumRows-oldNumRows;
			if (diff<0){
				for (int i=0;i>diff;i--){
					for (int col =0;col<numColumnsProperty.get();col++){
						AuthoringMapStackPane AMSP = this.getChildAtPosition(oldNumRows-i-1, col);
						if (AMSP.isActive()){
							this.removeActive(AMSP);
						}
						if (AMSP.isCoveredByOtherSprite()){
							AbstractSpriteObject ASO = AMSP.getCoveringSprite();
							AuthoringMapStackPane parentSP = (AuthoringMapStackPane)ASO.getParent();
							parentSP.setRowSpan(parentSP.getRowSpan()-1);
							
						}
					this.getChildren().remove(AMSP);
					}
				}
			} else if (diff>0){
				for (int i=0;i<diff;i++){
					for (int col =0;col<numColumnsProperty.get();col++){
					this.addAuthoringStackPaneToPosition(oldNumRows+i, col);
					}
			}
			}
		});
		
		numColumnsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			;
			Integer diff = newNumColumns-oldNumColumns;
			if (diff<0){
				for (int i=0;i>diff;i--){
					for (int row =0;row<numRowsProperty.get();row++){
						;
						AuthoringMapStackPane AMSP = this.getChildAtPosition(row,oldNumColumns-i-1);
						if (AMSP.isActive()){
							this.removeActive(AMSP);
						}
						if (AMSP.isCoveredByOtherSprite()){
							AbstractSpriteObject ASO = AMSP.getCoveringSprite();
							AuthoringMapStackPane parentSP = (AuthoringMapStackPane)ASO.getParent();
							parentSP.setColSpan(parentSP.getColSpan()-1);
							
						}
					this.getChildren().remove(AMSP);
					}
				}
			} else if (diff>0){
				for (int i=0;i<diff;i++){
					for (int row =0;row<numRowsProperty.get();row++){
					this.addAuthoringStackPaneToPosition(row, oldNumColumns+i);
					}
			}
			}
		});
		mySGH = SGH;
		this.addAuthoringStackPaneToPosition(0,0);
		this.setNumRows(rows);
		this.setNumCols(columns);
		
	}
	

	public MapLayer(int rows, int columns, SpriteGridHandler SGH, Color c, List<AbstractSpriteObject> activeSpriteObjects) {
		this(rows, columns, SGH, c);
		for (AbstractSpriteObject ASO : activeSpriteObjects) {
			int x = ASO.getPositionOnGrid()[0];
			int y = ASO.getPositionOnGrid()[1];
			AuthoringMapStackPane child = this.getChildAtPosition(x, y);
			mySGH.addSpriteDrag(ASO);
			mySGH.addSpriteMouseClick(ASO);
			child.addChild(ASO);
		}
	}
	
	public void setFillColor(Color c){
		this.fillEmptyCellColor = c;
		for (Node node : this.getChildren()){
			if (node instanceof AuthoringMapStackPane){
				AuthoringMapStackPane AMSP = (AuthoringMapStackPane) node;
				if (!(AMSP.hasChild()|| AMSP.isCoveredByOtherSprite())){
					AMSP.setInactiveBackground(c);
				}
			}
			
		}
	}
	
	
	public void setBackgroundImage(String imagePath){
		this.setBackgroundImage(()->new SpriteObject(false));
	}
	
	public AbstractSpriteObject setBackgroundImage(Supplier<AbstractSpriteObject> spriteSupplier){
		return null;	
	}
	
	public void addMostRecentActive(AuthoringMapStackPane newMostRecentActive){
		mostRecentActive.add(newMostRecentActive);
	}
	
	public void setMostRecentActive(Set<AuthoringMapStackPane> newMostRecentActive){
		mostRecentActive = newMostRecentActive;
	}
	
	public Set<AuthoringMapStackPane> getMostRecentActive(){
		return mostRecentActive;
	}

	
	public void setSpriteGridHandler(SpriteGridHandler SGH){
		mySGH = SGH;
	}
	
	public void addActive(AuthoringMapStackPane pane){
		this.activeGridCells.add(pane);
	}
	
	public Set<AuthoringMapStackPane> getActive(){
		return activeGridCells;
	}
	
	public void removeActive(AuthoringMapStackPane pane){
		this.activeGridCells.remove(pane);
		if (this.getMostRecentActive().contains(pane)){
			this.getMostRecentActive().remove(pane);
		}
	}
	
	public void removeAllActive(){
		Set<AuthoringMapStackPane> activeSet = new HashSet<AuthoringMapStackPane>();
		this.activeGridCells.forEach(authMapStackPane->{
			activeSet.add(authMapStackPane);
		});
		activeSet.forEach(item->item.setInactive());
	}
	
	public String getName(){
		return myName;
	}
	
	protected void setName(String name)
	{
		myName = name;
	}

	protected void setDefaultColor(Color c) {
		defaultColor = c;
	}

	protected Color getDefaultColor() {
		return defaultColor;
	}
	
	public void removeSpritesAtPositions(List<Integer[]> cellsToDelete){
		cellsToDelete.forEach((pos)->{
			this.getChildAtPosition(pos[0], pos[1]).removeChild();
		});
	}
	
	public AuthoringMapStackPane getChildAtPosition(int row, int col){
		AuthoringMapStackPane result = null;
		if (this.getChildren() == null) ;
		ObservableList<Node> childrens = this.getChildren();
	    for (Node node : childrens) {
	        if(this.getRowIndex(node) == row && this.getColumnIndex(node) == col) {
	            result = (AuthoringMapStackPane) node;
	            break;
	        }
	    }
	    return result;
	}
	
	public int getCellSize(){
		return CELL_SIZE;
	}
	
	protected boolean hasChildAtPosition(int row, int column){
		AuthoringMapStackPane child = getChildAtPosition(row, column);
	    	return child.hasChild();
	}

	
	private AuthoringMapStackPane addAuthoringStackPaneToPosition(int row, int col){
		AuthoringMapStackPane sp = new AuthoringMapStackPane(this);
		sp.setOnMouseEntered(e -> style(sp));
		sp.setOnMouseExited(e -> removeStyle(sp));
		sp.setMinWidth(CELL_SIZE);
		sp.setMaxWidth(CELL_SIZE);
		sp.setPrefWidth(CELL_SIZE);
		sp.setMinHeight(CELL_SIZE);
		sp.setPrefHeight(CELL_SIZE);
		sp.setMaxHeight(CELL_SIZE);
		
		sp.setBackground(
				new Background(new BackgroundFill(getDefaultColor(), CornerRadii.EMPTY, Insets.EMPTY)));

		GridPane.setHgrow(sp, Priority.NEVER);
		GridPane.setVgrow(sp, Priority.NEVER);
		this.add(sp, col, row);
		sp.setColSpan(1);
		sp.setRowSpan(1);
		
		mySGH.addDropHandling(sp);
		mySGH.addGridMouseClick(sp);
		mySGH.addGridMouseDrag(sp);
		return sp;
	}
	
	private void style(StackPane sp) {
		sp.setStyle("-fx-border-color: #47BDFF;");
	}
	
	private void removeStyle(StackPane sp) {
		sp.setStyle("-fx-border-color: transparent;");
	}
	
	public void addRow(){
		setNumCols(numRowsProperty.get()+1);
	}
	
	public void setNumRows(Integer newRows){
		this.numRowsProperty.set(newRows);
	}
	
	public void addColumn(){
		setNumCols(numColumnsProperty.get()+1);
	}
	
	public void setNumCols(Integer newCols){
		this.numColumnsProperty.set(newCols);
	}

}
