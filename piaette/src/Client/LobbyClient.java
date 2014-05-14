package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LobbyClient {
	private OutputStream outputStream;
	private InputStream inputStream;
	private int playerId;
	private String playerName = "Sven";
	
	public LobbyClient(String machine, int port) {

		try {
			Socket socket = new Socket(machine, port);
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			
			if(handshake()){
				Thread waitForServer = new LobbyFromServer(socket);
				waitForServer.start();
				Thread waitForClient = new LobbyToServer(socket);
				waitForClient.start();
			}

		} catch (IndexOutOfBoundsException e) {
			System.out.println("You have to write both machine and port number");
		} catch (NumberFormatException e) {
			System.out.println("You suck");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean handshake() throws IOException{
		String initMessage = receiveMessage();
		if(!initMessage.equals("welcome")) return false;
		sendMessage("playerName: "+playerName);
		String idMessage = receiveMessage();
		playerId = Integer.parseInt(idMessage.substring(10));
		return true;
	}
	
	public void sendMessage(String msg) throws IOException {
		outputStream.write((msg).getBytes());
	}

	public String receiveMessage() throws IOException{
		int readByte;
		StringBuilder sb = new StringBuilder();
		do {
			readByte = inputStream.read();
			char a = (char) readByte;
			sb.append(a);
			System.out.print(a);
			if (a == '\n') {
				readByte = -1;
			}
		} while (readByte != -1);

		return sb.toString();
	}
	
	public static void startGame(){
		//TODO
	}
	public static void disconnectedByServer(){
		//TODO
	}
	
	public void disconnectByClient(){
		try {
			sendMessage("leaveGame");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayerId(){
		return playerId;
	}


}