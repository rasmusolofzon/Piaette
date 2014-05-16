package server;

import java.net.DatagramSocket;
import java.net.InetAddress;

import framtiden.GameMailBox;

import main.Utility;

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
		Utility.sendUDP(message, socket, IPAddress, port);
	}
}
