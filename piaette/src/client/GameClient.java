package client;

import game.Game;
import game.Player;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

import utilties.PlayerDefinition;

public class GameClient{
	private DatagramSocket socket;
	private InetAddress hostAddress;
	private int hostPort;
	private Game game;
	private Player player;
	private PlayerDefinition pDef;
	public GameClient(DatagramSocket socket, InetAddress hostAddress, int hostPort,Game game){
		System.out.println("Starting sending all that shit!");
		
		this.game = game;
		this.player = game.getPlayer();
		this.pDef = new PlayerDefinition(player.name,player.id);
		
		this.socket = socket;
		this.hostAddress = hostAddress;
		this.hostPort = hostPort;
		
		Thread down = new GameDownStream(this);
		Thread up = new GameUpStream(this);
		
		down.start();
		up.start();
	}
	
	public DatagramSocket getSocket(){
		return socket;
	}
	
	public InetAddress getHostAddress() {
		return hostAddress;
	}
	
	public int getHostPort() {
		return hostPort;
	}
	
	public void updatePlayer(int id, float x, float y, float r, float timer) {
		PlayerDefinition pDef = new PlayerDefinition(null,id);
		pDef.updateX(x);
		pDef.updateY(y);
		pDef.updateRotation(r);
		pDef.updateTimer(timer);
		game.updatePlayer(pDef);
	}
	
	public PlayerDefinition getPlayerInfo() {
		System.out.println(player.debugPrint());
		pDef.updateX(player.getX());
		pDef.updateY(player.getY());
		pDef.updateRotation(player.getDirection());
		pDef.updateTimer(player.score);
		return pDef;
	}

}
