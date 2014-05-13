package server;

public class PlayerDefinition {

	private int id;
	private String playerName;

	public PlayerDefinition(String playerName, int i) {
		this.id = i;
		this.playerName = playerName;
	}
	
	public String getName(){
		return playerName;
	}
	public int getId(){
		return id;
	}

}
