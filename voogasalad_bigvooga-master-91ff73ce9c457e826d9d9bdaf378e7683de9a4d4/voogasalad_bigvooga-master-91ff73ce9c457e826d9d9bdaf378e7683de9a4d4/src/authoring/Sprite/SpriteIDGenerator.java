package authoring.Sprite;

import java.util.UUID;

public class SpriteIDGenerator {
	
	private static SpriteIDGenerator instance = null;
//	private long currentTime;
//	private long extraDigit = 10;
	
	private SpriteIDGenerator(){
//		extraDigit = 10;
	}
	
	public static SpriteIDGenerator getInstance(){
		if (instance ==null){
			instance = new SpriteIDGenerator();
		}
		return instance;
	}
	
	public String getUniqueID(){
//		long newTime = System.currentTimeMillis();
//		if (newTime!=currentTime){
//			currentTime = newTime;
//			extraDigit = 10;
//			return Long.toString(newTime);
//		} else {
//			extraDigit++;
//			;
//			return Long.toString(newTime)+Long.toHexString(extraDigit);
//		}
	
//	}
		return UUID.randomUUID().toString();
	}
}
