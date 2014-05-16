package client;


import utilties.PlayerDefinition;
import utilties.Protocol;
import utilties.ProtocolParser;
import utilties.ServerProtocol;
import utilties.comUtility;

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
			
			if (rcvProtocol.getProtocol()==Protocol.PROTOCOL_SERVER) {
				ServerProtocol sp = (ServerProtocol) rcvProtocol;
				if (sp.getSequenceNumber()<=SEQ) {
					continue;
				}
				
				for (PlayerDefinition p : sp.getPlayers()) {
					model.updatePlayer(p.getId(), p.getX(), p.getY(), p.getRotation(), p.getTimer());
				}
				SEQ=sp.getSequenceNumber();
			}
		}
	}
}
