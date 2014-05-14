package protocol;

public class ProtocolPlayer {
	private int pid,tmr;
	private float x,y,rot;
	
	public ProtocolPlayer(int pid, float x, float y,float rot,int tmr) {
		this.pid = pid;
		this.x = x;
		this.y = y;
		this.rot = rot;
		this.tmr = tmr;
	}
	public float getX() {return x;}
	public float getY() {return y;}
	public float getRotation() {return rot;}
	public int getTimer() {return tmr;}
	public int getPlayerId() {return pid;}
	public String toString() {return "ID=" + pid + ", X=" + x + ", Y=" + y + ", Rotation=" + rot + ", Timer=" + tmr;}
}
