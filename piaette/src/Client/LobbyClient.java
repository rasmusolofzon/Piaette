package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LobbyClient {
	private OutputStream outputStream;
	private InputStream inputStream;
	private int playerId;
	private String playerName;
	
	public LobbyClient(String machine, int port, String playerName) throws IOException,NumberFormatException{
		this.playerName = playerName;
			Socket socket = new Socket(machine, port);
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			
			if(handshake()){
				System.out.println("handshake succesful");
				Thread waitForServer = new LobbyFromServer(socket);
				waitForServer.start();
			}

	}
	
	private boolean handshake() throws IOException{
		String initMessage = receiveMessage();
		if(!initMessage.startsWith("welcome")) return false;
		sendMessage("playerName: "+playerName);
		String idMessage = receiveMessage();
		playerId = Integer.parseInt(idMessage.substring(10));
		System.out.println("Received id "+playerId+" from the server");
		return true;
	}
	
	public void sendMessage(String msg) throws IOException {
		outputStream.write((msg+'\n').getBytes());
	}

	public String receiveMessage() throws IOException{
		int readByte;
		StringBuilder sb = new StringBuilder();
		do {
			readByte = inputStream.read();
			char a = (char) readByte;
			sb.append(a);
			if (a == '\n') {
				readByte = -1;
			}
		} while (readByte != -1);
		return sb.toString().trim();
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