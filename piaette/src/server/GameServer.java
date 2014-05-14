package server;




public class GameServer {
	private int serverPort = 22222;
	Thread serverOffer, clientAdder;
	public GameServer(String serverName){
		
		Thread serverOffer = new ServerOffer(serverPort);
		serverOffer.start();
		
		Thread clientAdder = new ServerLobby(serverPort);
		clientAdder.start();
		
	}
	public void close() {
		serverOffer.interrupt();
		clientAdder.interrupt();
		return;
		
	}

	
}
