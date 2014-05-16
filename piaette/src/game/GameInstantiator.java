package game;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import utilties.PlayerDefinition;

public class GameInstantiator extends Thread {
	public static int width = 1000;
	public static int height = 9 * width / 16;
	public static float scale = 1f;
	public static int fpsLimit = 60;
	private Game game;

	private ArrayList<PlayerDefinition> pDefs;
	private int playerId;

	public GameInstantiator(ArrayList<PlayerDefinition> pDefs, int playerId) {
		this.pDefs = pDefs;
		this.playerId = playerId;

		try {
			System.setProperty("org.newdawn.slick.pngloader", "false");
			game = new Game(pDefs, playerId);
			AppGameContainer app = new AppGameContainer(game);
			app.setDisplayMode((int) (width * scale), (int) (height * scale),
					false);
			app.setTargetFrameRate(fpsLimit);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public Game getGame() {
		return game;
	}

}
