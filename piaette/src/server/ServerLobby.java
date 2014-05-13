package server;

import java.net.ServerSocket;
import java.net.Socket;


public class ServerLobby extends Thread {

	private int serverPort;
	private static LobbyMailBox mailBox;

	public ServerLobby(int serverPort) {
		this.serverPort = serverPort;
	}
	
	@Override
	public void run(){
		try {
			ServerSocket serverSocket = new ServerSocket(serverPort);
			System.out.println("Created ChatServer");
			while (true) {
				Socket socket = serverSocket.accept();

				ClientHandler cH = new ClientHandler(socket);
				cH.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static LobbyMailBox getMailBox(){
		if(mailBox==null) mailBox = new LobbyMailBox();
		return mailBox;
	}

}
