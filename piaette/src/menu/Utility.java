package menu;

import java.awt.Font;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Utility {
	
	
	@SuppressWarnings("unchecked")
	public static UnicodeFont getNewFont(String fontName , int fontSize) {
		UnicodeFont font = new UnicodeFont(new Font(fontName , Font.PLAIN , fontSize));
		font.addGlyphs("@");
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		return font;
	}
}
