package Client;

import main.Utility;
import protocol.Protocol;
import protocol.ProtocolParser;
import protocol.ServerProtocol;
import server.PlayerDefinition;

public class GameDownStream extends Thread {
	private GameClient model;
	private ProtocolParser parser;
	private int SEQ;
	public GameDownStream(GameClient model){
		//TODO
		this.model = model;
		this.parser = ProtocolParser.getInstance();
		this.SEQ = -1;
	}
	@Override
	public void run(){
		while(true){
			String receive = Utility.receiveUDP(model.getSocket());
			Protocol proto = parser.parse(receive);
			if (proto.getProtocol()!=Protocol.PROTOCOL_SERVER) {
				continue;
			}
			
			ServerProtocol c = (ServerProtocol) proto;
			if (!(c.getSequenceNumber()>SEQ)) {
				continue;
			}
			
			for (PlayerDefinition p : c.getPlayers()) {
				model.updatePlayer(p.getId(), p.getX(), p.getY(), p.getRotation(), p.getTimer());
			}
		}
	}
}
