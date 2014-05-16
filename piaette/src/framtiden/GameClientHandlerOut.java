package framtiden;

import java.net.DatagramSocket;
import java.net.InetAddress;

import utilties.PlayerDefinition;
import utilties.comUtility;





public class GameClientHandlerOut extends Thread {
	private GameMailBox mailBox;
	private PlayerDefinition pDef;
	private DatagramSocket socket;
	private InetAddress IPAddress;
	private int port;
	
	public GameClientHandlerOut(GameMailBox mailBox, DatagramSocket socket, InetAddress IPAddress, int port, 
			PlayerDefinition pDef) {
		this.mailBox = mailBox;
		this.socket = socket;
		this.IPAddress = IPAddress;
		this.port = port;
		this.pDef = pDef;
	}
	
	public void updateClient(String message) {
		comUtility.sendUDP(message, socket, IPAddress, port);
	}
}
