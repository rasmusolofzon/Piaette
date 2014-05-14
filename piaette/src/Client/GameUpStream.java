package Client;

import main.Utility;
import protocol.ClientProtocol;
import server.PlayerDefinition;

public class GameUpStream extends Thread {
	private GameClient model;
	private int SEQ;
	private long lastSend;
	
	public GameUpStream(GameClient model){
		this.model = model;
		this.SEQ = 0;
	}

	public void run(){
		while(true) {
			long now = System.currentTimeMillis();
			
			if (now - lastSend >= (long) 100) {
				PlayerDefinition p = model.getPlayerInfo();
				String msg = new ClientProtocol(SEQ,p.getId(),p.getX(),p.getY(),p.getRotation()).toString();
				Utility.sendUDP(msg, model.getSocket(), model.getHostAddress(), model.getHostPort());
				lastSend = now;
				SEQ++;
			}
		}
	}
}
