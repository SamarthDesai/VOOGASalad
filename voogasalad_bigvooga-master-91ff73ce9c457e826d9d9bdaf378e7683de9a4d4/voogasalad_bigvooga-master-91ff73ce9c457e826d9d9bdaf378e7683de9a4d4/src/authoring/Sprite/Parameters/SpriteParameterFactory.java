package authoring.Sprite.Parameters;

public class SpriteParameterFactory {
	
	public static SpriteParameter makeParameter(String name, Object value){
		if (value instanceof String) {
			return new StringSpriteParameter(name, (String) value);
		} else if (value instanceof Boolean) {
			return new BooleanSpriteParameter(name, (Boolean) value);
		} else if (value instanceof Double){
			return new DoubleSpriteParameter(name, (Double) value);
		}
		return null;
	}

}
