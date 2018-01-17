package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.DialogSprite.AuthoringDialogSequence;
import authoring_UI.MapManager;
import authoring_UI.displayable.DisplayableManager;
import engine.utilities.data.GameDataHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that represents the pane containing all dialogue authoring components
 * 
 * @author DavidTran and Dara Buggay
 *
 */
public class DialogueManager extends DisplayableManager {

	private static final double NODE_SPACING = 20;
	private static final double BUTTON_WIDTH = 300;
	private static final double BUTTON_HEIGHT = 75;
	private static final String ADD_BUTTON_PROMPT = "New";
	private static final String SAVE_BUTTON_PROMPT = "Save";
	private static final String DELETE_BUTTON_PROMPT = "Delete";

	private HBox hb;
	private DialogueEditor currentEditor;
	private DialogueTabPane dView;
	private List<DialogueEditor> editorList;
	private int currentEditorIndex = 0;
	private DialogueExtractor dExtractor;
	private DialogueListView listView;

	private Tab mapDialoguesTab;
	private GameDataHandler GDH;
	private AuthoringEnvironmentManager AEM;

	public DialogueManager(AuthoringEnvironmentManager currentAEM) {
		AEM = currentAEM;
		GDH = AEM.getGameDataHandler();
		dView = new DialogueTabPane();
		editorList = new ArrayList<>();
		dExtractor = new DialogueExtractor();
		hb = new HBox(NODE_SPACING);
		hb.setLayoutX(10);
		hb.getChildren().addAll(dView, createSeparator(), createButtonPanel());

	}
	
	public void addDialogueListener(Tab dialoguesTab) {
		mapDialoguesTab = dialoguesTab;
		System.out.println("YIPPEE");
		updateListView();
	}

	public HBox getPane() {
		return hb;
	}

	@Override
	protected Separator createSeparator() {
		return super.createSeparator();
	}

	@Override
	protected Separator createShortSeparator(int height) {
		return super.createShortSeparator(height);
	}
	
	@Override
	protected void updateListView() {
		dExtractor.extract(editorList);
		listView = new DialogueListView(dExtractor.getDialogueList());

		mapDialoguesTab.setContent(listView);
	}


	@Override
	protected void save() {
		if (currentEditor != null && !currentEditor.getName().trim().equals("")) {
			if (!currentEditor.getBackgroundIsColor()) {
				AuthoringDialogSequence dialogSequence = new AuthoringDialogSequence(currentEditor.getName(), currentEditor.getDialogueSequence(), currentEditor.getBackgroundImage());
				try{
				AEM.getDialogSpriteController().addNewSuperlayerSequence(dialogSequence);
				} catch (Exception e){
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Couldn't save dialogue");
					alert.setContentText("try again");
					alert.showAndWait();
					}
				}
			else if (currentEditor.getBackgroundIsColor()) {
				AuthoringDialogSequence dialogSequence = new AuthoringDialogSequence(currentEditor.getName(), currentEditor.getDialogueSequence(), currentEditor.getBackgroundColor());
				try {
				AEM.getDialogSpriteController().addNewSuperlayerSequence(dialogSequence);
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Couldn't save dialogue");
					alert.setContentText("try again");
					alert.showAndWait();
				}
			}
			
			if (editorList.contains(currentEditor)) {
				editorList.remove(currentEditor);
			}
			editorList.add(currentEditorIndex, currentEditor);

			addUserDialogueButton(currentEditor.getName());
		}

		updateListView();
	}

	protected void newEditor() {
		currentEditor = new DialogueEditor(e -> save(), GDH);
		currentEditorIndex = editorList.size();

		loadEditor(currentEditorIndex);
	}

	protected void loadEditor(int index) {
		
		if (hb.getChildren().size() >= 4) {
			hb.getChildren().remove(5 - 1);
			hb.getChildren().remove(4 - 1);

		}

		if (editorList.size() <= index) {
			hb.getChildren().addAll(createShortSeparator(300), currentEditor.getParent());
		} else {
			hb.getChildren().addAll(createShortSeparator(300), editorList.get(index).getParent());
			currentEditor = editorList.get(index);

		}

		currentEditorIndex = index;
	}

	protected VBox createButtonPanel() {
		VBox vb = new VBox(NODE_SPACING);
		vb.getChildren().addAll(createButton(ADD_BUTTON_PROMPT, e -> newEditor()),
				createButton(SAVE_BUTTON_PROMPT, e -> save()), createButton(DELETE_BUTTON_PROMPT, e -> delete()));
		return vb;
	}

	protected void prev() {
		if (currentEditorIndex > 0) {
			currentEditorIndex -= 1;
			hb.getChildren().remove(4);
			hb.getChildren().add(editorList.get(currentEditorIndex).getParent());
		}
	}

	protected void next() {
		if (currentEditorIndex < editorList.size() - 1) {
			currentEditorIndex += 1;
			hb.getChildren().remove(4);
			hb.getChildren().add(editorList.get(currentEditorIndex).getParent());
		}
	}

	private void addDefaultDialogueButton() {
		Button btn = new Button("Default Dialogue #1");
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		dView.addDefaultDialogueButton(0, btn);

	}

	private void addUserDialogueButton(String name) {
		Button btn = new Button(name);
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		dView.addUserDialogueButton(currentEditorIndex, btn);
		System.out.println(dView);
		btn.setOnAction(e -> loadEditor(dView.getButtonIndex(btn)));
	}

	protected void delete() {

		for(int i = 0; i < editorList.size(); i++) {
			
		}


		if (!editorList.isEmpty()) {
			
			removeUserDialogueButton();
			
			if (editorList.size() > 1) {

				if (currentEditorIndex == editorList.size() - 1) {
					prev();
					editorList.remove(currentEditorIndex + 1);

				} else {
					next();
					editorList.remove(currentEditorIndex - 1);
					currentEditorIndex -= 1;
				}
				
				currentEditor = editorList.get(currentEditorIndex);
			}

			else {
				hb.getChildren().remove(4);
				hb.getChildren().remove(3);
				editorList.remove(currentEditorIndex);
				currentEditorIndex -= 1;
			}
		}

		for(int i = 0; i < editorList.size(); i++) {
	
		}

	}

	private void removeUserDialogueButton() {
		int id = currentEditorIndex;
		dView.removeUserDialogueButton(id);
	}

}
