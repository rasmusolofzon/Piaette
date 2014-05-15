package Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.Utility;
import server.PlayerDefinition;

public class LobbyFromServer extends Thread {
	private InputStream in;
	int inputCase;

	public LobbyFromServer(Socket socket) throws IOException {
		in = socket.getInputStream();
	}

	public void run() {
		while (true) {
			doCase();
		}
	}

	private void doCase() {
		String input = "";
		try {
			input = Utility.receiveMessage(in);

			System.out.println("doCase returns: " + input);
			if (input.equalsIgnoreCase("startGame")) {
				System.out.println("Recieved startgame in doCase()");
				
				ArrayList<PlayerDefinition> pDefs = receivePlayers();
				
				
				LobbyClient.startGame(pDefs);
			} else if (input.equals("serverClosed")) {
				LobbyClient.disconnectedByServer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<PlayerDefinition> receivePlayers() throws IOException {
		ArrayList<PlayerDefinition> pDefs = new ArrayList<PlayerDefinition>();
		String input = Utility.receiveMessage(in);
		//TODO!
		return null;
	}

}
