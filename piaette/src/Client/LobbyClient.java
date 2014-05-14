package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import main.Utility;

public class LobbyClient {
	private OutputStream outputStream;
	private InputStream inputStream;
	private int playerId;
	private String playerName;
	private static InetAddress hostAddress;
	private static int hostPort;
	
	public LobbyClient(String machine, int port, String playerName) throws IOException,NumberFormatException{
		hostAddress = InetAddress.getByName(machine);
		hostPort = port+1;
		
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
	
	private boolean handshake() {
		try {
			String initMessage = Utility.receiveMessage(inputStream);
			if(!initMessage.startsWith("welcome")) return false;
			Utility.sendMessage(outputStream,"playerName: "+playerName);
			String idMessage = Utility.receiveMessage(inputStream);
			playerId = Integer.parseInt(idMessage.substring(10));
			System.out.println("Received id "+playerId+" from the server");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void startGame(){
		System.out.println("Trying to start game");
		try {
			System.out.println("Recieved startgame");
			new GameClient(new DatagramSocket(),hostAddress,hostPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public static void disconnectedByServer(){
	}
	
	public void disconnectByClient(){
		try {
			Utility.sendMessage(outputStream,"leaveGame");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayerId(){
		return playerId;
	}


}