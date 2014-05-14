package main;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	
	public static String receiveMessage(InputStream in) throws IOException {
		int readByte;
		StringBuilder sb = new StringBuilder();
		do {
			readByte = in.read();
			char a = (char) readByte;
			sb.append(a);
			if (a == '\n') {
				readByte = -1;
			}
		} while (readByte != -1);

		return sb.toString().trim();
	}
	public static void sendMessage(OutputStream outputStream, String msg) throws IOException {
		outputStream.write((msg+'\n').getBytes());
	}
}
