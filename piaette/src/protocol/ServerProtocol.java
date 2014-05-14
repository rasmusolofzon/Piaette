package protocol;

public class ServerProtocol extends Protocol {
	private int sequenceNbr,piaette;
	private ProtocolPlayer[] pp;
	public ServerProtocol(int seq,ProtocolPlayer[] players,int piaetteId) {
		super(Protocol.PROTOCOL_SERVER);
		this.sequenceNbr = seq;
		this.pp = players;
		this.piaette = piaetteId;
	}
	public int getSequenceNumber() {return sequenceNbr;}
	public ProtocolPlayer[] getPlayers() {return pp;}
	public int getPiaetteId() {return piaette;}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SRV:");
		sb.append(sequenceNbr +":");
		sb.append(pp.length + ":");
		for (ProtocolPlayer p : pp) {
			sb.append(p+":");
		}
		sb.append(piaette);
		
		return sb.toString();
	}
}
