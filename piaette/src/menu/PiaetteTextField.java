package menu;

import main.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.TextField;


/*
 * render() måste kallas i render-loopen (antar jag)
 * och update() måste kallas i update-loopen.
 */
public class PiaetteTextField extends TextField {
	private GameContainer gc;
	private UnicodeFont font;
	
	/*
	 * Short hand for standard set-up of text field. 
	 * Centered, 300 wide, standard colours.
	 */
	public PiaetteTextField(GameContainer gc, UnicodeFont font, int y){
		this(gc,font,(Main.width-300)/2,y,300,32);
		setBackgroundColor(new Color(170, 170, 170));
		setTextColor(Color.white);
	}
	
	public PiaetteTextField(GameContainer gc, UnicodeFont font, int x, int y, int width, int height){
		super(gc,font,x,y,width,height);
		this.font = font;
		this.gc = gc;
	}
	
	public void render(Graphics g) throws SlickException{
		font.loadGlyphs();	
		super.render(gc, g);
	}
}
