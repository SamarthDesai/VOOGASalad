package authoring.Sprite.Parameters;

import java.util.List;

import authoring.Sprite.Parameters.*;
import javafx.scene.layout.VBox;

/**
 * factory class that handles taking all back end sprite parameters and converting them to editable front end UI parameters, displayed in a vbox
 * 
 * @author Dara Buggay
 *
 */
public class FEParameterFactory extends VBox {

	public FEParameterFactory(List<SpriteParameter> newParams) {
		for (SpriteParameter BEParam : newParams) {
			createFEParameter(BEParam);
		}
		this.setSpacing(5);
	}

	private void createFEParameter(SpriteParameter BEParam) {
		if (BEParam instanceof BooleanSpriteParameter) {
			FEBooleanParameter newBoolean = new FEBooleanParameter(BEParam);
			this.getChildren().add(newBoolean);
		} else if (BEParam instanceof StringSpriteParameter) {
			FEStringParameter newString = new FEStringParameter(BEParam);
			this.getChildren().add(newString);
		} else if (BEParam instanceof DoubleSpriteParameter) {
			FEDoubleParameter newDouble = new FEDoubleParameter(BEParam);
			this.getChildren().add(newDouble);
		}
	}

	public static FEParameter makeFEParameter(SpriteParameterI SPI) {
		FEParameter ret = null;
		if (SPI instanceof BooleanSpriteParameter) {
			ret = new FEBooleanParameter(SPI);
		} else if (SPI instanceof StringSpriteParameter) {
			ret = new FEStringParameter(SPI);
		} else if (SPI instanceof DoubleSpriteParameter) {
			ret = new FEDoubleParameter(SPI);
		}
		return ret;
	}
}
