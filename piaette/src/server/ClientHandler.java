package server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import utilties.comUtility;




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
			if(handShake()){
				boolean run = true;
				while(run){
					String command = readNextMessage();
					run = parseCommand(command);
				}
			}

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	private boolean handShake() throws IOException {
		sendMessage("welcome");
		String response1 = readNextMessage();
		if(response1.startsWith("playerName:")){
			String playerName = response1.substring(12);
			System.out.println("Found "+playerName);
			int id = mailBox.addClient(this);
			pDef = new PlayerDefinition(playerName,id);
			sendMessage("playerId: "+pDef.getId());
			mailBox.notifyObservers();
			return true;
		}
		return false;
	}

	private boolean parseCommand(String command) {
		if(command.equals("leaveGame")){
			ServerLobby.getMailBox().removeClient(this);
			return false;
		}
		return true;
	}

	public void sendMessage(String msg) throws IOException {
		System.out.println("ClientHandler: " + msg);
//		outputStream.write((msg+'\n').getBytes());
		
		comUtility.sendMessage(outputStream, msg);
	}

	public String readNextMessage() throws IOException{
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
	
	public PlayerDefinition getPlayer(){
		return pDef;
	}

}
