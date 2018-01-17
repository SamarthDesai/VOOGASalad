package authoring_actionconditions;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

/**
 * ActionConditionTabUtil--contains a few useful methods for doing things in the actionConditionTab
 * @author Owen Smith
 *
 */
public class ActionConditionTabUtil {
	
	private static final String SPLITTER = ",";
	
	/**
	 * makeVerticalSeparator--makes a vertical separtor used in the vboxes and topHBox
	 * @return
	 */
	protected static Separator makeVerticalSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}
	
	/**
	 * convertToObservableList--takes a comma separated string from resources and converts it into an observable list
	 * @param resourceString--the resourceString that needs to be converted into an observable list
	 * @return
	 */
	protected static ObservableList<String> convertToObservableList(String resourceString) {
		String[] optionsSplit = resourceString.split(SPLITTER);
		return FXCollections.observableList(Arrays.asList(optionsSplit));
	}
}
