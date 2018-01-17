package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import ActionConditionClasses.ApplyButtonController;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterSidebarManager;
import authoring.DialogSprite.AuthoringDialogSequence;
import authoring.Sprite.AbstractSpriteObject;
import controller.authoring.AuthoringController;
import engine.operations.VoogaType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import sun.security.tools.keytool.Resources;

/**
 * Class that creates a dropdown of existing variables/objects in the game.
 * 
 * @author DavidTran
 *
 */
public class ExistingItemsChoiceBox {

	private static final String PACKAGE_NAME = ExistingItemsChoiceBox.class.getPackage().getName();
	private static final String KEY_BUNDLE_LOCATION = PACKAGE_NAME + ".codes";
	private ChoiceBox<String> cb;
	private ResourceBundle keys;

	private AbstractSpriteObject selectedSprite = ApplyButtonController.selectedSpriteObject;
	private List<AbstractSpriteObject> sprites;
	private Supplier<List<AbstractSpriteObject>> supplier;

	// need current sprites

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param supplier
	 */
	public ExistingItemsChoiceBox(VoogaType type, Supplier<List<AbstractSpriteObject>> supplier) {
		this.supplier = supplier;
		keys = Resources.getBundle(KEY_BUNDLE_LOCATION);
		
		this.createSpriteList();

		List<String> list = this.makeObservableList(type);

		if (list.size() >= 0)
			cb = this.makeChoiceBox(list);
	}

	private void createSpriteList() {
		Map<String, List<AbstractSpriteObject>> map = AuthoringEnvironmentManager.getEveryTypeOfSprite();
		sprites = map.get("DefaultSprites");
		sprites.addAll(map.get("CustomSprites"));
		sprites.addAll(supplier.get());
		System.out.println("GOT SPRITES IN GRID");
		// sprites.addAll(SpriteParameterSidebarManager.getAllSpritesFromActiveGrid());
	}

	private List<String> makeObservableList(VoogaType type) {

		List<String> list = new ArrayList<>();

		if (type == VoogaType.ANIMATIONNAME) {

			list.addAll(selectedSprite.getAnimationSequenceNames());
			// TODO
			list.add("animations should be showing above");

		} else if (type == VoogaType.BOOLEANNAME) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getParameterNamesMatching("Boolean"));
			}

		} else if (type == VoogaType.DOUBLENAME) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getParameterNamesMatching("Double"));
			}

		} else if (type == VoogaType.DIALOGNAME) {

			if (selectedSprite.getDialogSequences() == null)
				System.out.println("dialog list is nulllll for selected sprite");
			else {
				for (AuthoringDialogSequence d : selectedSprite.getDialogSequences()) {
					list.add(d.getName());
				}
			}

			// TODO
			list.add("dialogues should be shown above");

		} else if (type == VoogaType.KEY) {
			list = makeKeyList();

		} else if (type == VoogaType.OBJECTNAME) {
			list = AuthoringEnvironmentManager.getNameOfEverySprite();

		} else if (type == VoogaType.STRINGNAME) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getParameterNamesMatching("String"));
			}

		} else if (type == VoogaType.TAG) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getTags());
			}

		} else if (type == VoogaType.WORLDNAME) {

			// AuthoringEnvironmentManager.get
			// TODO
			list.add("need access to world names");
			list.add("need access to world names");

		} else {
			list.add("if this is here, then it is broken");
			list.add("pls tell david if you see this");
		}
		;
		;
		return list;
	}

	private ChoiceBox<String> makeChoiceBox(List<String> list) {
		ObservableList<String> obList = FXCollections.observableArrayList(list);
		ChoiceBox<String> cb = new ChoiceBox<>(obList);
		return cb;
	}

	private List<String> makeKeyList() {
		List<String> list = new ArrayList<>();
		list.addAll(keys.keySet());
		return list;
	}

	/****************** PUBLIC METHODS ********************/

	public ChoiceBox<String> getChoiceBox() {
		return cb;
	}

	public String getSelected() {
		return "";
	}
}
