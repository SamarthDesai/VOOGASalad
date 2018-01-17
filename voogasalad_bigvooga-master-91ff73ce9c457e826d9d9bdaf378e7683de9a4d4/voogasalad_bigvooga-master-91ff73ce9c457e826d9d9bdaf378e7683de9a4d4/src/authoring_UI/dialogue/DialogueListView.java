package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * Class that displays a list of dialogues.
 * 
 * @author DavidTran
 *
 */
public class DialogueListView extends ListView<String> {

	private static double HEIGHT = 20;
	private List<String> dList = new ArrayList<>();
	private int dialogueCount = 0;

	public DialogueListView(List<Dialogue> list) {

		this.setHeight(HEIGHT);

		for (Dialogue d : list) {
			dList.add(d.getName());
			dialogueCount++;
		}
		ObservableList<String> items = FXCollections.observableArrayList(dList);

		this.setItems(items);

	}

	private String createListCellText(Dialogue d) {
		TextField styled = new TextField(d.getName());
		styled.setFont(Font.font(d.getFontType()));
		styled.setStyle(d.getFontColor().toString());
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return dialogueCount + ":" + separator + " " + styled;
	}
}
