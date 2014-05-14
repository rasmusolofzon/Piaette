package Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class LobbyFromServer extends Thread {
	private InputStream in;
	int inputCase;

	public LobbyFromServer(Socket socket) throws IOException {
		in = socket.getInputStream();
	}

	public void run() {
		while (true) {
			try {
				doCase();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void doCase() throws IOException {
		String input = readInput();
		if(input.equals("startGame")){
			LobbyClient.startGame();
		}else if(input.equals("serverClosed")){
			LobbyClient.disconnectedByServer();
		}
	}

	public String readInput() throws IOException {
		int readByte;
		StringBuilder sb = new StringBuilder();
		do {
			readByte = in.read();
			char a = (char) readByte;
			sb.append(a);
			if (a == '\n') {
				readByte = -1;
			}
		} while (readByte != -1);

		return sb.toString();
	}
}
