package Client;

import main.Utility;

public class GameDownStream extends Thread {
	private GameClient model;
	public GameDownStream(GameClient model){
		//TODO
		this.model = model;
	}
	@Override
	public void run(){
		while(true){
			String receive = Utility.receiveUDP(model.getSocket());
		}
	}
}
