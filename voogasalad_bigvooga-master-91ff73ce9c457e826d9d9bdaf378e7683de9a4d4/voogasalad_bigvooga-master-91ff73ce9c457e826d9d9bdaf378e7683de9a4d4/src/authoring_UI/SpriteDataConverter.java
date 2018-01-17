package authoring_UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import authoring.DialogSprite.AuthoringDialogSequence;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.AnimationSequences.AuthoringAnimationSequence;
import authoring.Sprite.Parameters.SpriteParameter;
import engine.Action;
import engine.Condition;
import engine.utilities.data.GameDataHandler;

public class SpriteDataConverter {

	GameDataHandler myGDH;
	Map<Condition, List<Integer>> conditionRows;
	List<Action> actionRows;
	Map<String, List<SpriteParameter>> catmap;
	List<SpriteDataConverter> inventory;
	String imageURL;
	Integer[] gridPos;
	String name;
	Integer width;
	Integer height;
	String UUID;
	List<AuthoringAnimationSequence> myAnimationSequences;
	List<String> spriteConditionOperations;
	List<List<String>> spriteActionOperations;
	Integer renderingPreference;

	List<AuthoringDialogSequence> myDialogs;
	String mySavePath;
	String spriteType;
	List<String> tags;

	public SpriteDataConverter(AbstractSpriteObject ASO) {
		convertSprite(ASO);
	}

	public String getName() {
		return name;
	}

	public SpriteDataConverter getToSerialize() {
		return this;
	}

	public void setPath(String path) {
		mySavePath = path;
	}

	public void convertSprite(AbstractSpriteObject ASO) {
		myDialogs = ASO.getDialogSequences();
		catmap = ASO.getParameters();
		gridPos = ASO.getPositionOnGrid();
		name = ASO.getName();
		width = ASO.getNumCellsWidth();
		height = ASO.getNumCellsHeight();
		UUID = ASO.getUniqueID();
		imageURL = ASO.getImageURL();
		mySavePath = ASO.getSavePath();
		tags = ASO.getTags();
		inventory = new ArrayList<SpriteDataConverter>();
		renderingPreference = ASO.getRenderingPreference();
		conditionRows = ASO.getConditionRows();
		actionRows = ASO.getActionRows();
		myAnimationSequences = ASO.getAnimationSequences();
		spriteConditionOperations = ASO.getSelectedConditionOperations();
		spriteActionOperations = ASO.getSelectedActionOperations();
		ASO.getInventory().forEach(sprite -> {
			inventory.add(new SpriteDataConverter(sprite));
		});
		if (ASO instanceof SpriteObject) {
			spriteType = "SpriteObject";
		} else if (ASO instanceof InventoryObject) {
			spriteType = "InventoryObject";
		}
		myGDH = null;
	}

	public void setGameDataHandler(GameDataHandler GDH) {
		myGDH = GDH;
	}

	public AbstractSpriteObject createSprite() {

		AbstractSpriteObject ret = null;

		if (spriteType.equals("SpriteObject")) {
			ret = new SpriteObject(true, myGDH);
		} else if (spriteType.equals("InventoryObject")) {
			ret = new InventoryObject(true, myGDH);
		} else {
			ret = new SpriteObject(true, myGDH);
		}
		ret.setParameterMap(catmap);
		ret.setPositionOnGrid(gridPos);
		myAnimationSequences.forEach(seq -> seq.setGameDataHandler(myGDH));
		ret.setAnimationSequences(myAnimationSequences);
		ret.setRenderingPreference(renderingPreference);
		ret.setDialogSequences(myDialogs);
		ret.setNumCellsHeightNoException(height);
		ret.setNumCellsWidthNoException(width);
		ret.setUniqueID(UUID);
		ret.setName(name);
		ret.setSavePath(mySavePath);
		ret.setTags(tags);

		ret.setSelectedConditionOperations(spriteConditionOperations);
		ret.setSelectedActionOperations(spriteActionOperations);

		ret.setConditionRows(conditionRows);
		ret.setActionRows(actionRows);
		ret.setSelectedConditionOperations(spriteConditionOperations);
		ret.setSelectedActionOperations(spriteActionOperations);
		List<AbstractSpriteObject> newInventory = new ArrayList<AbstractSpriteObject>();
		inventory.forEach(SDC -> {
			newInventory.add(SDC.createSprite());
		});
		ret.setInventory(newInventory);
		System.out.println("spriteInventoryinSDC: " + ret.getInventory());
		System.out.println("Sprite Converter ImageURL: " + imageURL);
		ret.setImageURL(imageURL);

		ret.setGameDataHandler(myGDH);
		// ret.setIsLoadingFromXML(true);

		return ret;
	}

}
