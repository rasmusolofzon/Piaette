package Client;

import java.net.DatagramSocket;
import java.net.InetAddress;

import server.PlayerDefinition;

public class GameClient {
	private DatagramSocket socket;
	private InetAddress hostAddress;
	private int hostPort;
	public GameClient(DatagramSocket socket, InetAddress hostAddress, int hostPort){
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
		// TODO Auto-generated method stub
	}
	
	public PlayerDefinition getPlayerInfo() {
		PlayerDefinition temp = new PlayerDefinition("none",1);
		temp.updateX(100);
		temp.updateY(200);
		temp.updateRotation(300);
		temp.updateTimer(30);
		return temp;
	}
}
