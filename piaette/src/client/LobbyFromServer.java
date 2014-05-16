package client;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;


import server.PlayerDefinition;
import utilties.Protocol;
import utilties.ProtocolParser;
import utilties.ServerProtocol;
import utilties.comUtility;

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
			input = comUtility.receiveMessage(in);

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
			String input = comUtility.receiveMessage(in);
			Protocol pp = parser.parse(input);
			if (pp.getProtocol()==Protocol.PROTOCOL_SERVER) {
				ServerProtocol srv = (ServerProtocol) pp;
				System.out.println("Bäver: " + srv.getPlayers().size());
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
