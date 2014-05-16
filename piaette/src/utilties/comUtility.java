package utilties;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class comUtility {

//DENNA BORDE INTE FINNAS MEN OM DEN INTE FINNS BLIR FRAMTIDEN LEDSEN I ÖGAT
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
		outputStream.flush();
	}

	public static String receiveUDP(DatagramSocket socket){
		try {
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			socket.receive(receivePacket);
			String message = new String(receivePacket.getData());
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void sendUDP(String message, DatagramSocket socket,InetAddress IPAddress,int port){
		byte[] sendData = message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, IPAddress, port);
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
