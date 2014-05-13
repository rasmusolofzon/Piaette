package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

	private OutputStream outputStream;
	private InputStream inputStream;
	private LobbyMailBox mailBox;
	private PlayerDefinition pDef;

	public ClientHandler(Socket socket) throws IOException {
		outputStream = socket.getOutputStream();
		inputStream = socket.getInputStream();
		mailBox = ServerLobby.getMailBox();
	}

	@Override
	public void run(){
		try{
			
			handShake();
			
			while(true){
				String command = readNextMessage();
				parseCommand(command);
				
			}
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	private void handShake() throws IOException {
		sendMessage("welcome");
		String response1 = readNextMessage();
		if(response1.startsWith("playerName:")){
			String playerName = response1.substring(12);
			System.out.println("Found "+playerName);
			pDef = mailBox.addPlayer(playerName);
			sendMessage("playerId: "+pDef.getId());
		}
		
	}

	private void parseCommand(String command) {
		switch(command){
			case "startGame":
				//TODO
				break;
			case "leaveGame":
				//TODO
				break;
			default:
				//TODO
				break;
		}
		
	}

	public void sendMessage(String msg) throws IOException {
		outputStream.write((msg).getBytes());
	}

	public String readNextMessage() throws IOException{
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

}
