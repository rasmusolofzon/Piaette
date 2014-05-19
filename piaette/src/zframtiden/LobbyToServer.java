package zframtiden;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class LobbyToServer extends Thread {
	private OutputStream os;
	private Socket socket;
	private String msg;

	public LobbyToServer(Socket socket) throws IOException {
		os = socket.getOutputStream();
		this.socket = socket;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public void run() {
		try {
			os.write(msg.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
