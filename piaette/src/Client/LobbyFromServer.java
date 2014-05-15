package Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.Utility;
import protocol.Protocol;
import protocol.ProtocolParser;
import protocol.ServerProtocol;
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

	private ArrayList<PlayerDefinition> receivePlayers() {
		try {
			ProtocolParser parser = ProtocolParser.getInstance();
			String input = Utility.receiveMessage(in);
			Protocol pp = parser.parse(input);
			if (pp.getProtocol()==Protocol.PROTOCOL_SERVER) {
				ServerProtocol srv = (ServerProtocol) pp;
				System.out.println("B�ver: " + srv.getPlayers().size());
				return srv.getPlayers();
			}else{
				return receivePlayers();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
