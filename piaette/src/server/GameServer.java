package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Vector;

import protocol.ServerProtocol;




public class GameServer {
	Thread serverOffer, clientAdder;
	private Vector<PlayerDefinition> players;
	private Vector<SocketAddress> udpClients;
	
	public GameServer(int serverPort, String serverName){
		
		Thread serverOffer = new ServerOffer(serverPort);
		serverOffer.start();
		
		Thread clientAdder = new ServerLobby(serverPort);
		clientAdder.start();
		DatagramSocket udpSocket = null;
		try {
			udpSocket = new DatagramSocket(serverPort+1);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		udpClients = new Vector<SocketAddress>();
		
		new ServerUDPReceiver(udpSocket,udpClients).start();
		new ServerUDPSender(udpSocket,udpClients).start();
		
	}
	public void close() {
		serverOffer.interrupt();
		clientAdder.interrupt();
		return;
	}

	public class ServerUDPReceiver extends Thread {
		private DatagramSocket udpSock;
		private Vector<SocketAddress> udpClients;
		public ServerUDPReceiver(DatagramSocket udpSock,Vector<SocketAddress> udpClients) {
			this.udpSock = udpSock;
			this.udpClients = udpClients;
		}
		
		public void run() {
			while(true) {
				byte[] rcvBuffer = new byte[65000];
				DatagramPacket rcvPacket = new DatagramPacket(rcvBuffer,rcvBuffer.length);
				try {
					udpSock.receive(rcvPacket);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				SocketAddress sa = rcvPacket.getSocketAddress();
				if (!udpClients.contains(sa)) {
					udpClients.add(sa);
				}
				
				System.out.println("Received: '" + new String(rcvPacket.getData()).trim());
			}
		}
	}
	
	public class ServerUDPSender extends Thread {
		private DatagramSocket udpSock;
		private Vector<SocketAddress> udpClients;
		public ServerUDPSender(DatagramSocket udpSock,Vector<SocketAddress> udpClients) {
			this.udpSock = udpSock;
			this.udpClients = udpClients;
		}
		
		public void run() {
			long lastSend = 0;
			while (true) {
				long now = System.currentTimeMillis();
				if ((now - lastSend)>=100) {
					for (SocketAddress sa : udpClients) {
						byte[] sndTemp = new ServerProtocol(2,null,2).toString().getBytes();
						DatagramPacket snd = new DatagramPacket(sndTemp,sndTemp.length,sa);
						try {
							udpSock.send(snd);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					lastSend = now;
				}
			}
		}
	}
}
