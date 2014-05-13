package server;

import java.util.ArrayList;

public class LobbyMailBox {
	ArrayList<PlayerDefinition> players;
	private final int MAX_PLAYERS = 4;
	public LobbyMailBox(){
		players = new ArrayList<PlayerDefinition>();
	}
	
	public synchronized PlayerDefinition addPlayer(String playerName) {
		PlayerDefinition pDef = new PlayerDefinition(playerName,players.size()+1);
		players.add(pDef);
		return pDef;
	}
	
	public boolean isServerFull(){
		return players.size()==MAX_PLAYERS;
	}
	
}
