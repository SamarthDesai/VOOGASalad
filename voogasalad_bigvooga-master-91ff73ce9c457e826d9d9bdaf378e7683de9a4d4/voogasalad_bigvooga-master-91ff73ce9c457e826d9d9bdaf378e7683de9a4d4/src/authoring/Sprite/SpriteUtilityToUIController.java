package authoring.Sprite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

import authoring.Sprite.AbstractSpriteObject.*;
import authoring.util.NumberSpinner;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class SpriteUtilityToUIController {

	private HBox containerHbox;
	private BiFunction<String, Object, Boolean> myMethodOnValueChange;
	private AbstractSpriteObject myASO;
	private String setMethodSignature;
//	private boolean isLocked;

	public SpriteUtilityToUIController() {
		setMethodOnValueChange((string, object)->{
			return true;
		});
	}
	
	public void setSpriteObject(AbstractSpriteObject ASO){
		myASO = ASO;
	}

	public SpriteUtilityToUIController(BiFunction<String, Object, Boolean> methodOnValueChange) {
		setMethodOnValueChange(methodOnValueChange);
	}

	public SpriteUtilityUI getUIComponent(Annotation[] a, Field f) {
		Object o = null;
		try {
			o = (Object) f.get(myASO);
//			;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < a.length; i++) {
			Annotation b = a[i];
			if (b instanceof IsUnlockedUtility) {
				return getUnlockedUIComponent((IsUnlockedUtility) b, o);
			} else if (b instanceof IsLockedUtility) {
				return getLockedUIComponent((IsLockedUtility) b, o);
			}
		}
		return null;
	}
	
//	private void setSetMethodSignature(String t){
//		setMethodSignature = t;
//	}
//	private String getSetMethodSignature(){
//		return setMethodSignature;
//	}

	private SpriteUtilityUI getUnlockedUIComponent(IsUnlockedUtility a, Object o) {
		String getMethod = a.getMethod();
		String setMethod = a.setMethod();
		String readableName = a.readableName();
		
//		setSetMethodSignature(setMethod);

		HBox p = new HBox(20);

		p.setAlignment(Pos.CENTER);
		
		Label nameLabel = new Label();
		nameLabel.setText(readableName);
//		isLocked = false;
		Node content = getRequiredValueComponent(o, getMethodOnValueChange(), new String(setMethod), false);

		p.getChildren().addAll(nameLabel, content);

		SpriteUtilityUI SUUI = new SpriteUtilityUI(p);

		return SUUI;

	}

	private SpriteUtilityUI getLockedUIComponent(IsLockedUtility a, Object o) {
		String getMethod = a.getMethod();
		String readableName = a.readableName();
		
//		isLocked = true;

		HBox p = new HBox(20);
		
		p.setAlignment(Pos.CENTER);

		Label nameLabel = new Label();
		nameLabel.setText(readableName);
//		nameLabel.se

		Node content = getRequiredValueComponent(o, getMethodOnValueChange(), "", true);
		content.setDisable(true);
		p.getChildren().addAll(nameLabel, content);

		SpriteUtilityUI SUUI = new SpriteUtilityUI(p);

		return SUUI;

	}

	private Node getRequiredValueComponent(Object o, BiFunction<String, Object, Boolean> BiFunctionToCheck, String setMethod, Boolean isLocked) {
//		;
		if (o instanceof String) {
			TextField ret = new TextField();
			ret.setText((String) o);
			if (!isLocked){
			ret.textProperty().addListener((observable, previous, next) -> {
				if (!BiFunctionToCheck.apply(setMethod, next)){
					ret.textProperty().set(previous);
				}
			});
			}
			return ret;
		} else if (o instanceof Integer) {
			NumberSpinner ret = new NumberSpinner(new BigDecimal((Integer)o), BigDecimal.ONE);
			ret.setCheckFunction(new Function<Integer, Boolean>(){
				@Override
				public Boolean apply(Integer t) {
//					;
					if (isLocked){
						return false;
					}
					boolean ret = BiFunctionToCheck.apply(setMethod, t);
//					;
					return ret;
				}
				
			});
//			if (!isLocked){
//			ret.numberProperty().addListener((observable, previous, next) -> {
//				if (!consumer.apply(setMethod, next.intValue())){
//					ret.setNumber(previous);
//				}
//			});
//			}
			return ret;
		} else if (o instanceof Boolean) {

		} else if (o instanceof Integer[]) {
			Label text = new Label();
			Integer [] asIntArray = (Integer[]) o;
			text.setText("Row: "+asIntArray[0]+" ; Column:  " + asIntArray[1]);
			return text;
		}
		Text emptyText = new Text("NOVALUE");
		return emptyText;
	}

	private BiFunction<String, Object, Boolean> getMethodOnValueChange() {
		return myMethodOnValueChange;
	}

	private void setMethodOnValueChange(BiFunction<String, Object, Boolean> consumer) {
		myMethodOnValueChange = consumer;
	}

}
