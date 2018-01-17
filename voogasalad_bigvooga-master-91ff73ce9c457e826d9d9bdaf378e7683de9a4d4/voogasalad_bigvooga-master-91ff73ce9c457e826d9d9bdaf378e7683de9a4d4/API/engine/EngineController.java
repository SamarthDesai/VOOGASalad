package engine;

public interface EngineController {
	public void start();
	public void addListener(Runnable listener);
	public void addWorld(World w);
	public void setCurrentWorld(String s);
}
