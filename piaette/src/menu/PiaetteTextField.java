package menu;

import main.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.TextField;


/*
 * render() måste kallas i render-loopen
 */
public class PiaetteTextField extends TextField {
	private GameContainer gc;
	private UnicodeFont font;
	private String title;
	
	/*
	 * Short hand for standard set-up of text field. 
	 * Centered, 300 wide, standard colours.
	 */
	public PiaetteTextField(GameContainer gc, UnicodeFont font, int y,String title){
		this(gc,font,(Main.width-300)/2,y,300,32);
		
		setBackgroundColor(new Color(170, 170, 170));
		setTextColor(Color.white);
;
		this.title = title;
		this.font = font;
		this.gc = gc;
	}
	
	private PiaetteTextField(GameContainer gc, UnicodeFont font, int x, int y, int width, int height){
		super(gc,font,x,y,width,height);
	}
	public void setText(String input){
		super.setText(input);
	}
	public void  setConsumeEvents(boolean consume){
		super.setConsumeEvents(consume);
	}
	
	public void render(Graphics g) throws SlickException{
		//Title
		Color c = g.getColor();
		g.drawString(title, this.getX(), this.getY()-this.getHeight()/2);
		g.setColor(c);
		
		font.loadGlyphs();
		super.render(gc, g);
	}
}
