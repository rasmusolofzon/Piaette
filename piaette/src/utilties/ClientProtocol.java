package utilties;

public class ClientProtocol extends Protocol {
	private int sequenceNbr,playerId;
	private float x,y,rot;
	public ClientProtocol(int seq,int pid, float x, float y,float rot) {
		super(Protocol.PROTOCOL_CLIENT);
		this.sequenceNbr = seq;
		this.playerId = pid;
		this.x = x;
		this.y = y;
		this.rot = rot;
	}
	public int getSequenceNumber() {return sequenceNbr;}
	public int getPlayerID() {return playerId;}
	public float getX() {return x;}
	public float getY() {return y;}
	public float getRotation() {return rot;}
	public String toString() {return "CLI:" + sequenceNbr + ":" + playerId + ":" + x + ":" + y + ":" + rot;}
}
