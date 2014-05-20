package client;

import game.GameInstantiator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import utilities.PlayerDefinition;
import utilities.ComUtility;

public class LobbyClient {
	private OutputStream outputStream;
	private InputStream inputStream;
	private static int playerId;
	private String playerName;
	private static InetAddress hostAddress;
	private static int hostPort;

	public LobbyClient(String machine, int port, String playerName)
			throws IOException, NumberFormatException {
		hostAddress = InetAddress.getByName(machine);
		hostPort = port + 1;

		this.playerName = playerName;
		Socket socket = new Socket(machine, port);
		outputStream = socket.getOutputStream();
		inputStream = socket.getInputStream();

		if (handshake()) {
			System.out.println("handshake succesful");
			Thread waitForServer = new LobbyFromServer(socket);
			waitForServer.start();
		}

	}

	private boolean handshake() {
		try {
			String initMessage = ComUtility.receiveMessage(inputStream);
			if (!initMessage.startsWith("welcome"))
				return false;
			ComUtility.sendMessage(outputStream, "playerName: " + playerName);
			String idMessage = ComUtility.receiveMessage(inputStream);
			playerId = Integer.parseInt(idMessage.substring(10));
			System.out.println("Received id " + playerId + " from the server");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void startGame(ArrayList<PlayerDefinition> pDefs) {
		System.out.println("Trying to start game");
		System.out.println("Recieved startgame");
		new GameInstantiator(pDefs, playerId, hostAddress, hostPort);
	}

	public static void disconnectedByServer() {
	}

	public void disconnectByClient() {
		try {
			ComUtility.sendMessage(outputStream, "leaveGame");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPlayerId() {
		return playerId;
	}

}