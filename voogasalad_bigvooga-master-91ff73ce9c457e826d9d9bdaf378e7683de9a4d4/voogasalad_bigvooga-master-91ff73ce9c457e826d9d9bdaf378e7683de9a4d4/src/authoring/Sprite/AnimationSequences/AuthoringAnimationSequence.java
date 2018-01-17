package authoring.Sprite.AnimationSequences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import authoring.Thumbnail;
import engine.utilities.data.GameDataHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class AuthoringAnimationSequence {

	private String myName;
	private ArrayList<AnimationSequenceImage> myImages;
	private ScrollPane myScrollPane;
	private VBox myContainerVbox;
	private VBox outMostVbox;
	private ObjectProperty<AnimationSequenceImage> myMostRecentlyAddedAnimationSequenceProperty;
	private boolean showUI;
	private Button addNewImage;
	private ObjectProperty<Boolean> isEmpty;
	private Runnable myRunnableOnEmpty;
	
	public AuthoringAnimationSequence(String name) {
		myName = name;
		initialize();
	}

	public AuthoringAnimationSequence(AuthoringAnimationSequence AS) {
		initialize();
		myName = new String(AS.getName());
		AS.getImages().forEach(aasequence->{
			myImages.add(new AnimationSequenceImage(aasequence));
		});
//		myImages = AS.getImages();
	}

	public AuthoringAnimationSequence(String name, AuthoringImageView AEI) {
		this(name);
		addNewAuthoringImageViewToSequence(AEI, true);
	}
	
	public AuthoringAnimationSequence(String name, List<AnimationSequenceImage> images) {
		this(name);
		images.forEach(image -> {
			myImages.add(image);
		});
	}

	public AuthoringAnimationSequence(String name, ArrayList<AuthoringImageView> images) {
		this(name);
		images.forEach(image -> {
			addNewAuthoringImageViewToSequence(image, false);
		});
	}

	private void initialize() {
		if (myRunnableOnEmpty==null){
			setRunnableOnEmpty(()->{});
		}
		isEmpty = new SimpleObjectProperty<Boolean>();
		isEmpty.addListener((change, oldValue, newValue)->{
			if (newValue){
				getRunnableOnEmpty().run();
			}
		});
		myImages = new ArrayList<AnimationSequenceImage>();
	}
	
	public void replacePrimaryAnimationSequenceImage(AuthoringImageView AIV){
		if (myContainerVbox!=null){
		myContainerVbox.getChildren().remove(0);
		addNewAuthoringImageViewToSequence(AIV, true);
		} else {
			if (this.myImages.size()>0){
				myImages.remove(0);
			}
			this.myImages.add(0,new AnimationSequenceImage(AIV));
		}
		
	}
	
	public void setGameDataHandler(GameDataHandler GDH){
		myImages.forEach(image->image.setGameDataHandler(GDH));
	}
	
	public void setRunnableOnEmpty(Runnable r){
		myRunnableOnEmpty = r;
	}
	
	public Runnable getRunnableOnEmpty(){
		return myRunnableOnEmpty;
	}

	public void addNewAuthoringImageViewToSequence(AuthoringImageView AEI, Boolean isPrimary) {
		if (myMostRecentlyAddedAnimationSequenceProperty == null) {
			myMostRecentlyAddedAnimationSequenceProperty = new SimpleObjectProperty<AnimationSequenceImage>();
			
		myMostRecentlyAddedAnimationSequenceProperty.addListener((observable, oldASI, newASI) -> {
				if (showUI) {
					this.addNewAnimationSequenceToUI(newASI, isPrimary);
				}
				int position = isPrimary ? 0 : myImages.size();
				this.myImages.add(position, newASI);
			});
		
		}
		
		myMostRecentlyAddedAnimationSequenceProperty.set(new AnimationSequenceImage(AEI));
		
		

	}

//	private void addAuthoringImageView(AuthoringImageView AEI) {
//		myImages.add(new AnimationSequenceImage(AEI));
//	}

	public String getName() {
		return myName;
	}

	public ArrayList<AnimationSequenceImage> getImages() {
		return myImages;
	}
	
	public VBox getUIContent(){
		if (!haveShownUI()){
			outMostVbox = new VBox();
			outMostVbox.getChildren().addAll(getScrollPane());
		}
		return outMostVbox;
	}

	public ScrollPane getScrollPane() {
		if (myScrollPane == null) {
			createScrollPane();
			putContainerVBoxIntoScrollPane();
		}
		return myScrollPane;
	}
	
	public boolean haveShownUI(){
		if (!showUI){
			showUI = true;
			return false;
		}
		return true;
		
	}

	private void createScrollPane() {
		myScrollPane = new ScrollPane();
		myScrollPane.setPrefHeight(200);
		myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		
	}
	
	private void putContainerVBoxIntoScrollPane(){
		myScrollPane.setContent(getContainerVbox());
	}

	private VBox getContainerVbox() {
		if (myContainerVbox == null) {
			createContainerVbox();
			addAllAnimationSequenceImageThumbnails();
		}
		return myContainerVbox;
	}

	private void createContainerVbox() {
		myContainerVbox = new VBox();
		myContainerVbox.setPrefWidth(495);
		
	}

	private void addAllAnimationSequenceImageThumbnails() {
		for (AnimationSequenceImage ASI : this.getImages()) {
			addNewAnimationSequenceToUI(ASI, false);
		}
//		int counter = 1;
//		for (AnimationSequenceImage ASI : this.getImages()) {
//			String label = "Image " + Integer.toString(counter);
//			addNewAnimationSequenceToUI(ASI);
//			counter++;
//		}
	}

	private void addNewAnimationSequenceToUI(AnimationSequenceImage ASI, String label) {
		
		Thumbnail th = new Thumbnail(ASI.getImage(), label);
		th.addSideButton("Remove from animation");
		th.setSideButtonRunnable(()->{
			myContainerVbox.getChildren().remove(th);
			this.myImages.remove(ASI);
		});
		
		myContainerVbox.getChildren().add(th);
	}

	private void addNewAnimationSequenceToUI(AnimationSequenceImage ASI, Boolean isPrimary) {
		int vboxSize = myContainerVbox.getChildren().size();
		;
		Thumbnail th = new Thumbnail(ASI.getImage(), "Image "+Integer.toString(vboxSize));
		VBox animationBox = new VBox();
		
		animationBox.getChildren().addAll(th, new Separator());
		th.addSideButton("Remove from animation");
		th.setSideButtonRunnable(()->{
			if(!(this.getName().equals("Default")&&this.myContainerVbox.getChildren().size()==1)){
			myContainerVbox.getChildren().remove(animationBox);
			this.myImages.remove(ASI);
			}
			if (myContainerVbox.getChildren().size()==0){
				isEmpty.set(true);
			}
		});
		int insertionPos = isPrimary ? 0 : myContainerVbox.getChildren().size();
		myContainerVbox.getChildren().add(insertionPos, animationBox);
	}

	
	private Object writeReplace() throws java.io.ObjectStreamException {
		return new SerializableAuthoringAnimationSequence(this);
	}
}
