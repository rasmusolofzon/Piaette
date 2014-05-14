package Client;

public class GameClient {
	public GameClient(){
		Thread down = new GameDownStream();
		Thread up = new GameUpStream();
		
		down.start();
		up.start();
	}
}
