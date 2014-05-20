package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.PlayerDefinition;
import utilities.Protocol;
import utilities.ProtocolParser;
import utilities.ServerProtocol;
import utilities.ComUtility;

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
			input = ComUtility.receiveMessage(in);

	
			if (input.equalsIgnoreCase("startGame")) {
				System.out.println("Recieved startgame in doCase()");

				HashMap<Integer, String> playerNames = playerNames();

				ArrayList<PlayerDefinition> pDefs = receivePlayers();

				for (PlayerDefinition p : pDefs) {
					p.setName(playerNames.get(p.getId()));
				}

				LobbyClient.startGame(pDefs);
			} else if (input.equals("serverClosed")) {
				LobbyClient.disconnectedByServer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HashMap<Integer, String> playerNames() {
		HashMap<Integer, String> players = new HashMap<Integer, String>();
		String input;
		try {
			input = ComUtility.receiveMessage(in);
			String[] pNames = input.split(":");
			for (int i = 0; i < pNames.length; i++) {
				int pID = Integer.parseInt(pNames[i].split("#")[0]);
				String name = pNames[i].split("#")[1];
				players.put(pID, name);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return players;
	}

	private ArrayList<PlayerDefinition> receivePlayers() {
		try {
			ProtocolParser parser = ProtocolParser.getInstance();
			String input = ComUtility.receiveMessage(in);
			Protocol pp = parser.parse(input);
			if (pp.getProtocol() == Protocol.PROTOCOL_SERVER) {
				ServerProtocol srv = (ServerProtocol) pp;
				return srv.getPlayers();
			} else {
				return receivePlayers();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
