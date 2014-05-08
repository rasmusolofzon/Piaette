package menu;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;


/*
 * render() måste kallas i render-loopen (antar jag)
 * och update() måste kallas i update-loopen.
 */
public class PiaetteTextField extends TextField {
	private GameContainer gc;
	private static UnicodeFont font;
	
	public PiaetteTextField(GameContainer gc, int x, int y, int width, int height){
		super(gc,getNewFont("Arial",20),x,y,width,height);
	}
	
	public void render(Graphics g) throws SlickException{
		font.loadGlyphs();	
		super.render(gc, g);
		
	}
	
	public void update() throws SlickException{
		font.loadGlyphs();
	}

	private static UnicodeFont getNewFont(String fontName , int fontSize) {
		font = new UnicodeFont(new Font(fontName , Font.PLAIN , fontSize));
		font.addGlyphs("@");
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		return font;
	}
}
