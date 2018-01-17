package authoring_UI.cutscene;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for extracting all cutscene information and converting into Cutscene objects.
 * 
 * @author Dara Buggay
 *
 */
public class CutsceneExtractor {

	private List<Cutscene> cutsceneList;

	public CutsceneExtractor() {
		cutsceneList = new ArrayList<>();

	}

	protected void extract(List<CutsceneEditor> editorList) {
		cutsceneList = new ArrayList<>();

		for (CutsceneEditor ed : editorList) {
			cutsceneList.add(new Cutscene (ed.getName(), ed.getFontType(), ed.getFontColor(), ed.getCutsceneSequence()));
		}
	}

	public List<Cutscene> getCutsceneList() {
		return cutsceneList;
	}
}
