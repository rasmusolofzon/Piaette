package server;

import java.util.ArrayList;

public class LobbyMailBox {
	ArrayList<ClientHandler> players;
	private final int MAX_PLAYERS = 4;
	public LobbyMailBox(){
		players = new ArrayList<ClientHandler>();
	}
	
	public synchronized int addClient(ClientHandler clientHandler) {
		players.add(clientHandler);
		return players.size();
	}
	
	public boolean isServerFull(){
		return players.size()==MAX_PLAYERS;
	}
	
}
