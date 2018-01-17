package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SpriteScrollView extends ScrollPane {

	private VBox containerVBox;
	private Consumer<Pane> childOnClickAction;
	private List<ImageView> spriteList;
	private Map<Image, ImageView> imagesToImageViews;
	private String myTabPaneName;
	
	public SpriteScrollView() {
		spriteList = new ArrayList<ImageView>();
		createContainerVBox();
		putContainerVBoxIntoScrollPane();
		this.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		this.setChildOnClickAction(a -> {
			// Nothing by default
		});
	}
	
	public SpriteScrollView(String tabPaneName) {
		this();
		myTabPaneName = tabPaneName;
	}
	

	SpriteScrollView(List<Pane> panes) {

	}

	public void addToVBox(List<Pane> panes) {
		panes.forEach(pane -> {
			addToVBox(pane);
		});
	}

	public void addToVBox(Map<String, List<Pane>> panes) {
		TabPane tp = new TabPane();
		tp.setSide(Side.TOP);
		tp.setId(myTabPaneName);

		panes.forEach((key, value) -> {
			Tab tab = new Tab(key);
			SpriteScrollView SSV = new SpriteScrollView(myTabPaneName);
			SSV.addToVBox(value);
			SSV.setChildOnClickAction(this.childOnClickAction);
			tab.setContent(SSV);
			tp.getTabs().add(tab);
			// ScrollPane SP = new ScrollPane();
			// VBox VB = new VBox(5);
			// value.forEach(pane->);
		});
		addToVBox(new VBox(tp));
		// panes.forEach(pane->{
		// addToVBox(pane);
		// });
	}
	
	public void addToSpriteList(Image ASO) {
		ImageView imview = addToImageToImageViewMap(ASO);
		spriteList.add(imview);
	}
	
	public ImageView getFromImageToImageViewMap(Image im){
		if (this.imagesToImageViews!=null && this.imagesToImageViews.containsKey(im)){
		return this.imagesToImageViews.get(im);
		}
		return null;
	}
	
	private ImageView addToImageToImageViewMap(Image image){
		if (this.imagesToImageViews==null){
			this.imagesToImageViews = new HashMap<Image, ImageView>();
		}
		ImageView ret = new ImageView(image);
		this.imagesToImageViews.put(image, ret);
		return ret;
	}
	
	public void removeFromSpriteList(Image ASO) {
		ImageView imview = getFromImageToImageViewMap(ASO);
		spriteList.remove(imview);
	}

	public void addToSpriteList(ImageView ASO) {
		spriteList.add(ASO);
	}

	public void removeFromSpriteList(ImageView ASO) {
		spriteList.remove(ASO);
	}

	public List<ImageView> getSpriteList() {
		return spriteList;
	}

	private Separator getSeparator() {
		Separator ret = new Separator();
		return ret;
	}
	
	public void removeFromVBox(Pane pane){
		containerVBox.getChildren().remove(pane);
		
	}

	public void addToVBox(Pane pane) {
		pane.setOnMouseClicked(click -> {
			childOnClickAction.accept(pane);
		});
		containerVBox.getChildren().add(pane);
		if (containerVBox.getChildren().size() > 0) {
			containerVBox.getChildren().add(getSeparator());
		}		
	}
	
	public void addToVBoxNoSeparator(VBox vbox) {
		vbox.setOnMouseClicked(click -> {
			childOnClickAction.accept(vbox);
		});
		containerVBox.getChildren().add(vbox);
	}

	private void createContainerVBox() {
		containerVBox = new VBox();
		containerVBox.setPrefWidth(525);
	}

	private void putContainerVBoxIntoScrollPane() {
		this.setContent(containerVBox);
	}

	public void clearVBox() {
		containerVBox.getChildren().clear();
	}

	public void setChildOnClickAction(Consumer<Pane> consumer) {
		childOnClickAction = consumer;
	}
}