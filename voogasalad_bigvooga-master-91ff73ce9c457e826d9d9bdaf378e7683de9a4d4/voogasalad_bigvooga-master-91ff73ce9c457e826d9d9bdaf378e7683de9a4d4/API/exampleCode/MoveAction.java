package exampleCode;

import engine.Action;
import engine.GameObject;

public class MoveAction implements Action {

	private double dx;
	private double dy;

	public MoveAction(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public void execute(GameObject asking) {
		asking.setDoubleVariable("x", asking.getDoubleVariable("x")+dx);
		asking.setDoubleVariable("y", asking.getDoubleVariable("y")+dy);
	}

}
