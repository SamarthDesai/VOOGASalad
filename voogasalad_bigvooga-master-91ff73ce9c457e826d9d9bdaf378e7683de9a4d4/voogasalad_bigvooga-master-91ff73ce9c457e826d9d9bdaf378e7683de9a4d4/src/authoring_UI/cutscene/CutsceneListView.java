package authoring_UI.cutscene;

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
 * Class that displays a list of cutscenes
 * 
 * @author Dara Buggay
 *
 */
public class CutsceneListView extends ListView<String> {

		private static double HEIGHT = 20;
		private List<String> dList = new ArrayList<>();
		private int dialogueCount = 0;

		public CutsceneListView(List<Cutscene> list) {

			this.setHeight(HEIGHT);

			for (Cutscene d : list) {
				dList.add(d.getName());
				dialogueCount++;
			}
			ObservableList<String> items = FXCollections.observableArrayList(dList);

			this.setItems(items);

		}

		private String createListCellText(Cutscene c) {
			TextField styled = new TextField(c.getName());
			styled.setFont(Font.font(c.getFontType()));
			styled.setStyle(c.getFontColor().toString());
			Separator separator = new Separator();
			separator.setOrientation(Orientation.VERTICAL);
			return dialogueCount + ":" + separator + " " + styled;
		}
	}
