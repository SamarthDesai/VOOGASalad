package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.player.PlayerManager;
import engine.sprite.Displayable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Controls the game flow, and passes game info to PlayerManager.
 * @author Nikolas Bramblett, ...
 *
 */
public class GameMaster implements EngineController{
	private static final int DEFAULT_FPS = 60;
	private static final int DEFAULT_DELAY = 1000/DEFAULT_FPS;
	private static final String TRASH = "TRASH (unnamed save)";
	
	private GameWorld currentWorld;
	
	private List<GameWorld> madeWorlds;
	private Timeline gameLoop;
	private GlobalVariables globalVars;
	private PlayerManager playerManager;
	
	private GameObjectFactory blueprintManager;
	private String nextWorld;
	
	public GameMaster() {
		madeWorlds = new ArrayList<>();
		globalVars = new GlobalVariables();
	}
	
	/**
	 * Begins the gameloop, will continue until stop() is called.
	 */
	@Override
	public void start() {
		gameLoop = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(DEFAULT_DELAY), e -> step());
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
		gameLoop.play();
	}
	/**
	 * Ends Gameloop
	 */
	public void stop() {
		if(gameLoop != null)
			gameLoop.stop();
		gameLoop = null;
	}

	@Override
	public void addWorld(GameWorld w) {
		if(madeWorlds.isEmpty())
			currentWorld = w;
		madeWorlds.add(w);
	}
	
	/** 
	 * Sets which world will be displayed in Manager and be affected by the GameLoop.
	 * @param {String} s- Name of the world being set as current.
	 */
	@Override
	public void setNextWorld(String s) {
		nextWorld = s;
	}
	
	private void step() {
		if(nextWorld != null) {
			for(GameWorld world : madeWorlds) {
				if(world.isNamed(nextWorld))
					currentWorld = world;
			}
		}
		if(currentWorld == null)
			currentWorld = madeWorlds.get(0);
		ConcreteGameObjectEnvironment environment = new ConcreteGameObjectEnvironment();
		environment.setGameMaster(this);
		currentWorld.step(environment);
		imageUpdate();
		playerManager.step();
	}

	@Override
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	/**
	 * Passes image data to playermanager.
	 * Used in step.
	 */
	private void imageUpdate() {
		List<Displayable> imageData = new ArrayList<>();
		imageData.addAll(currentWorld.getAllDisplayables());
		playerManager.setImageData(imageData);
	}

	@Override
	public void addBlueprints(GameObjectFactory f) {
		blueprintManager = f;
	}

	public GameObjectFactory getBlueprints() {
		return blueprintManager;
	}

	public GameWorld getCurrentWorld()
	{
		return currentWorld;
	}

	public GameWorld getWorldWithName(String newWorld) {
		for(GameWorld world : madeWorlds) {
			if(world.isNamed(newWorld))
				return world;
		}
		return null;
	}

	public void save() {
		playerManager.save();;
	}
	
	public GlobalVariables getGlobals() {
		return globalVars;
	}
}
