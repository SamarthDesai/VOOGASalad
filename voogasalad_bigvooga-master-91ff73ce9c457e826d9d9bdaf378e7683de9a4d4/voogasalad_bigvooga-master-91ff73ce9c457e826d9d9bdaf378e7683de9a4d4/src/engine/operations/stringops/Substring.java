package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.doubleops.DoubleOperation;

public class Substring implements StringOperation {

	private StringOperation string;
	private DoubleOperation start;
	private DoubleOperation end;

	public Substring(@VoogaAnnotation(name = "String", type = VoogaType.STRING) StringOperation string,
			@VoogaAnnotation(name = "Start Index", type = VoogaType.DOUBLE) DoubleOperation start,
			@VoogaAnnotation(name = "End Index + 1", type = VoogaType.DOUBLE) DoubleOperation end) {
		this.string = string;
		this.start = start;
		this.end = end;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		String s = string.evaluate(asking, world);
		int first = (int) Math.round(start.evaluate(asking, world));
		int last = (int) Math.round(end.evaluate(asking, world));
		first = Math.max(0, first);
		last = Math.min(last, s.length());
		first = Math.min(first, last);
		return s.substring(first, last);
	}

}
