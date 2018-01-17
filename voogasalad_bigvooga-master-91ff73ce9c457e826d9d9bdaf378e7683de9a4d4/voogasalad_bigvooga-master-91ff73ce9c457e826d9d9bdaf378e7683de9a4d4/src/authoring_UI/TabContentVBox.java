package authoring_UI;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class TabContentVBox extends VBox{
	
	String myCategory;
	Tab parentTab;
	
	TabContentVBox(String category){
		super();
		myCategory = category;
		setSpacing(10);
	}
	
	public TabContentVBox(Tab parent){
		parentTab = parent;
		myCategory = parentTab.getText();
		setSpacing(10);
	}
	
	public String getMyCategory() {
		return myCategory;
	}

	public void setMyCategory(String myCategory) {
		this.myCategory = myCategory;
	}
	
	public String getCategory(){
		return parentTab.getText();
	}
}
