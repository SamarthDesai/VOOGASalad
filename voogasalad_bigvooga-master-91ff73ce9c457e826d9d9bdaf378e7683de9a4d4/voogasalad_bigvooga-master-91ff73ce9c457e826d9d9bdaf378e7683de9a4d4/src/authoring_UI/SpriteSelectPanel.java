package authoring_UI;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import javafx.scene.layout.VBox;

public class SpriteSelectPanel extends VBox {

	private String myName;
//	private SpriteManager mySM;
	private SpriteGridHandler mySGH;

	public SpriteSelectPanel(String name, SpriteGridHandler SGH){
		super();
		myName = name;
//		mySM = SM;
		mySGH = SGH;
	}
	
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}
	
	private void setDefaultSpriteVBox(List<AbstractSpriteObject> defaults) {
		this.getChildren().clear();
		defaults.forEach(SO -> {
			this.getChildren().add(SO);
		});

	}
	
	public void setupDefaultSprites(List<AbstractSpriteObject> list) {
		setDefaultSpriteVBox(list);
		makeDefaultSpritesDraggable(list);
		makeDefaultSpritesClickable(list);
	}
	
	private void makeDefaultSpritesDraggable(List<AbstractSpriteObject> defaults) {
		defaults.forEach(SO -> {
			makeSpriteDraggable(SO);
		});
	}
	
	private void makeSpriteDraggable(AbstractSpriteObject SO) {
		mySGH.addSpriteDrag(SO);
	}
	
	private void makeDefaultSpritesClickable(List<AbstractSpriteObject> defaults) {
		defaults.forEach(SO -> {
			makeSpriteClickable(SO);
		});
	}
	
	private void makeSpriteClickable(AbstractSpriteObject SO) {
        mySGH.addSpriteMouseClick(SO);
	}
	
	public void addNewSprite(SpriteObject SO){
		
	}
	
	public void addNewDefaultSprite(AbstractSpriteObject SO, int spriteLocation) {
		AbstractSpriteObject newSO = SO.newCopy();
		this.getChildren().add(spriteLocation, newSO);
		makeSpriteDraggable(newSO);
		makeSpriteClickable(newSO);
	}
	
	public void addNewDefaultSprite(AbstractSpriteObject SO) {
		AbstractSpriteObject newSO = SO.newCopy();
		int size = this.getChildren().size();
		this.getChildren().add(size, newSO);
		makeSpriteDraggable(newSO);
		makeSpriteClickable(newSO);
	}
	

	
}
