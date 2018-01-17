package authoring.Sprite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.function.BiFunction;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SpriteUtilityTabAndInfo {

	
	private ScrollPane containerScrollPane;
	private VBox containerVbox;
	private AbstractSpriteObject myASO;
	private SpriteUtilityToUIController mySUTUIC;

	

	public SpriteUtilityTabAndInfo(){
		mySUTUIC = new SpriteUtilityToUIController(getBiFunctionForValueOnChange());
		initialize();
	}
	
//	public void reset() {
//		myASO = null;
//		resetScrollPane();
//	}
	
	private void initialize(){
		makeContainerVBox();
		makeScrollPane();
		putVBoxIntoScrollPane();
	}
	
	public void setSpriteObject(AbstractSpriteObject ASO){
		myASO = ASO;
	}
	
	public void setSpriteObjectAndUpdate(AbstractSpriteObject ASO){
		myASO = ASO;
		mySUTUIC.setSpriteObject(ASO);
		clearVBox();
		addUtilityParametersToContainerVBox();
	}
	
	public void addUtilityParametersToContainerVBox(){
//		;
//		myASO = myASO.getClass().getSuperclass().cast(myASO);
		Field [] fields = myASO.getClass().getSuperclass().getDeclaredFields();
		Annotation[] as = myASO.getClass().getAnnotations();
//		;
//		;
		for (Field f:fields){
//			;
			Annotation[] a = f.getAnnotations();
//			 IsUnlockedUtility a = f.getAnnotation(IsUnlockedUtility.class);
			
			if (a!=null){
//				;
				SpriteUtilityUI UI_Component = mySUTUIC.getUIComponent(a, f);
//				;
				if (UI_Component!=null){
					addToVBox(UI_Component);
				}
			}
		}
	}
	
	private void addToVBox(Pane pane){
		containerVbox.getChildren().add(pane);
	}
	
	private void clearVBox(){
		containerVbox.getChildren().clear();
	}
	
	public Pane getContainerVBox(){
		return containerVbox;
	}
	
	private void makeContainerVBox(){
		containerVbox = new VBox(10);
		containerVbox.setPadding(new Insets(10, 0, 0, 10));
	}
	
	private void makeScrollPane(){
		containerScrollPane = new ScrollPane();
	}
	public ScrollPane getScrollPane(){
		return containerScrollPane;
	}
	
	
	
	public void putVBoxIntoScrollPane(){
		containerScrollPane.setContent(getContainerVBox());
	}
	
	private void sort(){
		containerVbox.getChildren().sort(new Comparator<Node>(){

			@Override
			public int compare(Node o1, Node o2) {
				int o1Val = 0;
				int o2Val = 0;
				if (o1 instanceof SpriteUtilityUI){
					o1Val = 1;
				} else if (o2 instanceof SpriteUtilityUI){
					o2Val = 1;
				}
				
				if (o1!=o2){
					return o2Val-o1Val;
				} else {
					return (((SpriteUtilityUI) o2).getValue() - ((SpriteUtilityUI) o1).getValue());
				}
			}
			
		});
	}
	

	
	private BiFunction<String, Object, Boolean> getBiFunctionForValueOnChange(){
		BiFunction<String, Object, Boolean> bifunc = new BiFunction<String, Object, Boolean>(){
			
			@Override
			public Boolean apply(String t, Object u) {
				
//				;
//				;
				Method setMethod = null;
				if (u!=null){
					try{
					if (u instanceof String){
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, String.class);
						setMethod.invoke(myASO, (String) u);
					}
						//TODO: Handle String Changes
					else if (u instanceof Double){
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, Double.class);
//						;
						setMethod.invoke(myASO, (Double) u);
						//TODO: Handle Double Changes
					}else if (u instanceof Integer){
//						;
//						;
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, Integer.class);
//						;
						setMethod.invoke(myASO, (Integer) u);
						//TODO: Handle Integer Changes
					} else if (u instanceof Boolean){
						//TODO: Handle Boolean Changes
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, Boolean.class);
					}
//					;
					return true;
				}
				 catch (Exception e){
//					e.printStackTrace();
					return false;
				}
					
				}
				
				return false;
			}
			
		};
		
		return bifunc;
	}
	
	
	
	
	
	
	
	
	
}
