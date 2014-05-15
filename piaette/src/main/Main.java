package main;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import shapes.Player;


public class Main {
	public static int width = 1000;
	public static int height = 9*width/16;
	public static float scale = 1f;
	public static int fpsLimit = 60;
	/**
	 * @param args
	 * Main-metod. Absurt ointressant. Borde kanske lägga alla tweak-värden här i.
	 */
	public Main(ArrayList<Player> players) {
		try {
			System.setProperty("org.newdawn.slick.pngloader", "false");
			AppGameContainer app = new AppGameContainer(new Game(players));
			app.setDisplayMode((int) (width*scale), (int) (height*scale), false);
			app.setTargetFrameRate(fpsLimit);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
