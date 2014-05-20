package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ComUtility {

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
			String message = new String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength(),"UTF-8");
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
			e.printStackTrace();
		}
	}

}
