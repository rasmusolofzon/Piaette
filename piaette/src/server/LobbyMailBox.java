package server;

import java.util.ArrayList;
import java.util.Observable;

public class LobbyMailBox extends Observable {
	static ArrayList<ClientHandler> players;
	private final int MAX_PLAYERS = 4;

	public LobbyMailBox() {
		players = new ArrayList<ClientHandler>();
	}
	
	public static void flush() {
		players.clear();
	}

	public synchronized int addClient(ClientHandler clientHandler) {
		players.add(clientHandler);
		setChanged();
		return players.size();
	}

	public void removeClient(ClientHandler clientHandler) {
		players.remove(clientHandler);
		setChanged();
		notifyObservers();
	}

	public boolean isServerFull() {
		return players.size() == MAX_PLAYERS;
	}

	public static ArrayList<ClientHandler> getClients() {
		return players;
	}

}
