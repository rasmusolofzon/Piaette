package protocol;

import java.util.ArrayList;

import server.PlayerDefinition;

public class ServerProtocol extends Protocol {
	private int sequenceNbr,piaette;
	private ArrayList<PlayerDefinition> pp;
	public ServerProtocol(int seq,ArrayList<PlayerDefinition> players,int piaetteId) {
		super(Protocol.PROTOCOL_SERVER);
		this.sequenceNbr = seq;
		this.pp = players;
		this.piaette = piaetteId;
	}
	public int getSequenceNumber() {return sequenceNbr;}
	public ArrayList<PlayerDefinition> getPlayers() {return pp;}
	public int getPiaetteId() {return piaette;}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SRV:");
		sb.append(sequenceNbr +":");
		sb.append(pp.size() + ":");
		for (PlayerDefinition p : pp) {
			sb.append(p+":");
		}
		sb.append(piaette);
		
		return sb.toString();
	}
}
