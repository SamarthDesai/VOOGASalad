package engine;

import controller.player.PlayerManager;

public interface EngineController {
	public void start();
	public void stop();
	public void setNextWorld(String s);
	public void setPlayerManager(PlayerManager currentPlayerManager);
	public void addWorld(GameWorld w);
	
	public void addBlueprints(GameObjectFactory f);
	public GameWorld getCurrentWorld();
}
