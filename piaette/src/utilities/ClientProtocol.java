package utilities;

public class ClientProtocol extends Protocol {
	private int sequenceNbr,playerId;
	private float x,y,rot;
	private long timer;
	public ClientProtocol(int seq,int pid, float x, float y,float rot,long timer) {
		super(Protocol.PROTOCOL_CLIENT);
		this.sequenceNbr = seq;
		this.playerId = pid;
		this.x = x;
		this.y = y;
		this.rot = rot;
		if (timer>30000) {
			this.timer = 300000;
		}else{
			this.timer = timer;
		}
	}
	public int getSequenceNumber() {return sequenceNbr;}
	public int getPlayerID() {return playerId;}
	public float getX() {return x;}
	public float getY() {return y;}
	public float getRotation() {return rot;}
	public long getTimer() {return timer;}
	public String toString() {return "CLI:" + sequenceNbr + ":" + playerId + ":" + x + ":" + y + ":" + rot+":"+timer;}
}
