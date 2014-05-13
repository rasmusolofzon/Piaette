package Client;

import java.io.IOException;
import java.net.Socket;

public class LobbyClient {
	private static String machine;
	private static int port;

	public LobbyClient() {

		try {

			machine = "localhost";
			port = Integer.parseInt("port");
			Socket socket = new Socket(machine, port);
			
			LobbyFromServer lFS = new LobbyFromServer(socket);
			lFS.start();
			
			
			//Olika saker ska ske vid olika inputs...
			LobbyToServer lTS = new LobbyToServer(socket);
			lTS.setMessage("hej");			
			lTS.start();



		} catch (IndexOutOfBoundsException e) {
			System.out
			.println("You have to write both machine and port number");
		} catch (NumberFormatException e) {
			System.out.println("You suck");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}