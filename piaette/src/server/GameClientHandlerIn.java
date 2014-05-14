package server;

import java.net.DatagramSocket;
import java.net.InetAddress;

import main.Utility;

public class GameClientHandlerIn extends Thread {
	private GameMailBox mailBox;
	private PlayerDefinition pDef;
	private DatagramSocket socket;
	private InetAddress IPAddress;
	private int port;
	
	public GameClientHandlerIn(GameMailBox mailBox, DatagramSocket socket, InetAddress IPAddress, int port, 
			PlayerDefinition pDef) {
		this.socket = socket;
		this.IPAddress = IPAddress;
		this.port = port;
		this.pDef = pDef;
	}
	
	public void updatePlayer() {
		String received = Utility.receiveUDP(socket);
		
		
		//Klient UDP: CLI:SEQ:PORT:X:Y
	}
}
