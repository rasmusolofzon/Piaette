package main;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class GameTest {

	public static int width = 900;
	public static int height = 9*width/16;
	public static float scale = 1f;
	public static int fpsLimit = 60;
	/**
	 * @param args
	 * Main-metod. Absurt ointressant. Borde kanske lägga alla tweak-värden här i.
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Game("Pjätt"));
			app.setDisplayMode((int) (width*scale), (int) (height*scale), false);
			app.setTargetFrameRate(fpsLimit);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}
