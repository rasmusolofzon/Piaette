package Client;

import java.io.IOException;
import java.net.Socket;

public class LobbyClient {
	private static String machine;
	private static int port;
	private static LobbyToServer lTS;

	public LobbyClient(String machine, int port) {

		try {
			Socket socket = new Socket(machine, port);
			
			LobbyFromServer lFS = new LobbyFromServer(socket);
			lFS.start();
						
			//Olika saker ska ske vid olika inputs...
			lTS = new LobbyToServer(socket);


		} catch (IndexOutOfBoundsException e) {
			System.out
			.println("You have to write both machine and port number");
		} catch (NumberFormatException e) {
			System.out.println("You suck");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ToServer(String output) {
		lTS.setMessage(output);			
		lTS.start();
		
	}



}