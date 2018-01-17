package authoring.Sprite.Parameters;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/**
 * Class that displays UI component for a double parameter (text area for name, text area for value)
 * 
 * @author Dara Buggay
 *
 */
public class FEDoubleParameter extends FEParameter {
	SpriteParameterI myParam;
	FEParameterName myName;
	TextArea myValue;
	
	protected FEDoubleParameter(SpriteParameterI BEParam) {
		myParam = BEParam;
		BorderStroke border = new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
		myName = new FEParameterName(myParam.getName());
		myName.setBorder(new Border(border));
		myValue = new TextArea(myParam.getValue().toString());
		myValue.setPrefWidth(170);
		myValue.setBorder(new Border(border));
		this.getChildren().addAll(myName, myValue);
		this.setMaxHeight(3);
		this.setSpacing(3);

		handleValueChange();
	}
	
	private void handleDouble() {
		myValue.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				myParam.checkError(newValue);
			} catch (Exception e) {
				displayErrorDialog(newValue, e);
				newValue = "";
			}
		});
	}
	
	private void displayErrorDialog(String value, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(e.getMessage());
		alert.setContentText("Please enter value for: " + myName.getText());

		alert.showAndWait();

	}
	
	protected void handleValueChange() {
		handleDouble();
	}
	
	public void updateParameter() {
		myParam.update(myName.getText(), Double.parseDouble(myValue.getText()));
	}

}
