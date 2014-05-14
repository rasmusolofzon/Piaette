package Client;

public class GameUpStream extends Thread {
	private GameClient model;
	private int SEQ;
	public GameUpStream(GameClient model){
		this.model = model;
		this.SEQ = 0;
	}

	@Override
	public void run(){
		while(true) {
			
		}
	}
}
