package game;

import java.net.InetAddress;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import utilities.PlayerDefinition;

public class GameInstantiator extends Thread {
	public static int width = 1000;
	public static int height = 9 * width / 16;
	public static float scale = 1f;
	public static int fpsLimit = 60;
	private Game game;

	private ArrayList<PlayerDefinition> pDefs;
	private int playerId;

	public GameInstantiator(ArrayList<PlayerDefinition> pDefs, int playerId,InetAddress hostAddress, int port) {
		this.pDefs = pDefs;
		this.playerId = playerId;

		try {
			System.setProperty("org.newdawn.slick.pngloader", "false");
			game = new Game(pDefs, playerId,hostAddress,port);
			AppGameContainer app = new AppGameContainer(game);
			app.setDisplayMode((int) (width * scale), (int) (height * scale),
					false);
			app.setShowFPS(false);
			app.setTargetFrameRate(fpsLimit);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		
	}
	public Game getGame() {
		return game;
	}

}
