package authoring.Sprite;

public class DefaultSpriteObject extends AbstractSpriteObject{
	
	 /**
	 * 
	 */

	public DefaultSpriteObject (){
		 setUniqueID();
	 }

	@Override
	public AbstractSpriteObject newCopy() {
		return new DefaultSpriteObject();
	}

}
