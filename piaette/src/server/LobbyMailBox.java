package server;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class LobbyMailBox extends Observable{
	static ArrayList<ClientHandler> players;
	private final int MAX_PLAYERS = 4;
	public LobbyMailBox(){
		players = new ArrayList<ClientHandler>();
	}

	public synchronized int addClient(ClientHandler clientHandler) {
		players.add(clientHandler);
		notifyObservers(this);
		return players.size();
	}
	
	public void removeClient(ClientHandler clientHandler){
		players.remove(clientHandler);
		notifyAll();
	}
	
	public boolean isServerFull(){
		return players.size()==MAX_PLAYERS;
	}
	
	public static ArrayList<ClientHandler> getClients(){
		return players;
	}
	
}
