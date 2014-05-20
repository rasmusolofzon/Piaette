package utilities;

public class PlayerDefinition {

	private int id;
	private String playerName;
	private float rotation, x, y;
	private long timer,heartbeat;

	public PlayerDefinition(String playerName, int i) {
		this.id = i;
		this.playerName = playerName;
	}
	
	public void setName(String name) {
		this.playerName = name;
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
	
	public long getTimer() {
		return timer;
	}

	public void updateX(float x) {
		this.x = x;
	}
	
	public void updateY(float y) {
		this.y = y;
	}
	
	public void updateRotation(float rotation) {
		this.rotation = (rotation % 360f) + 360f;//works
	}
	
	public void updateTimer(long tmr) {
		this.timer = tmr;
	}
	
	public boolean equals(PlayerDefinition p) {
		return this.id == p.id;
	}
	
	public long getLastHeartbeat() {return heartbeat;}
	public void setHeartbeat(long now) {this.heartbeat = now;}
	
	public String toString() {return id + "-" + x + "-" + y + "-" + rotation + "-" + timer;}
}
