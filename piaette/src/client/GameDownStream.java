package client;


import utilities.PlayerDefinition;
import utilities.Protocol;
import utilities.ProtocolParser;
import utilities.ServerProtocol;
import utilities.comUtility;

public class GameDownStream extends Thread {
	private GameClient model;
	private int SEQ;
	public GameDownStream(GameClient model){
		this.model = model;
		this.SEQ = -1;
	}
	@Override
	public void run(){
		ProtocolParser parser = ProtocolParser.getInstance();
		while(true){
			String receive = comUtility.receiveUDP(model.getSocket());
			Protocol rcvProtocol = parser.parse(receive);
			System.out.println("BLÅVALSKUK");
			if (rcvProtocol.getProtocol()==Protocol.PROTOCOL_SERVER) {
				ServerProtocol sp = (ServerProtocol) rcvProtocol;
				if (sp.getSequenceNumber()<=SEQ) {
					continue;
				}
				System.out.println("TIGERKUK");
				for (PlayerDefinition p : sp.getPlayers()) {
					model.updatePlayer(p.getId(), p.getX(), p.getY(), p.getRotation(), p.getTimer());
				}
				SEQ=sp.getSequenceNumber();
			}
		}
	}
}
