package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLobby extends Thread {

	private int serverPort;
	private static LobbyMailBox mailBox;
	private ServerSocket serverSocket;

	public ServerLobby(int serverPort) {
		this.serverPort = serverPort;
	}

	@Override
	public void run() {
		try {
			System.out.println("Starting server");
			serverSocket = new ServerSocket(serverPort);
			while (true) {
				if (!(serverSocket.isClosed())) {
					Socket socket = serverSocket.accept();
					ClientHandler cH = new ClientHandler(socket);
					cH.start();
				} else {
					return;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static LobbyMailBox getMailBox() {
		if (mailBox == null)
			mailBox = new LobbyMailBox();
		return mailBox;
	}

	public void closeSocket() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
