package server;

import java.util.ArrayList;
import java.util.Observable;

public class LobbyMailBox extends Observable{
	ArrayList<ClientHandler> players;
	private final int MAX_PLAYERS = 4;
	public LobbyMailBox(){
		players = new ArrayList<ClientHandler>();
	}
	
	public synchronized int addClient(ClientHandler clientHandler) {
		players.add(clientHandler);
		notifyAll();
		return players.size();
	}
	
	public void removeClient(ClientHandler clientHandler){
		players.remove(clientHandler);
		notifyAll();
	}
	
	public boolean isServerFull(){
		return players.size()==MAX_PLAYERS;
	}
	
	public ArrayList<ClientHandler> getClients(){
		return players;
	}
	
}
