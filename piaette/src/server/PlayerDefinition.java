package server;

public class PlayerDefinition {

	private int id;
	private String playerName;
	private float rotation, x, y, tmr;

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
	
	public float getRotation() {
		return rotation;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getTimer() {
		return tmr;
	}

	public void updateX(float x) {
		this.x = x;
	}
	
	public void updateY(float y) {
		this.y = y;
	}
	
	public void updateRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void updateTimer(float tmr) {
		this.tmr = tmr;
	}
}
