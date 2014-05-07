package server;

import java.net.ServerSocket;
import java.net.Socket;


public class GameServer {
	public GameServer(){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(30000);
			System.out.println("Created GameServer");
			while (true) {
				Socket socket = serverSocket.accept();

				//ClientHandler cH = new ClientHandler(socket);
				//cH.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
