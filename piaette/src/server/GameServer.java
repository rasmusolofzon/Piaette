package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.geom.Circle;

import utilities.ClientProtocol;
import utilities.PlayerDefinition;
import utilities.Protocol;
import utilities.ProtocolParser;
import utilities.ServerProtocol;





public class GameServer {
	Thread  clientAdder;
	private DatagramSocket udpSocket;
	private Vector<PlayerDefinition> players;
	private Vector<SocketAddress> udpClients;
	public static int chaser = 0;
	
	
	
	public GameServer(int serverPort, String serverName){
		
		clientAdder = new ServerLobby(serverPort);
		clientAdder.start();
		udpSocket = null;
		try {
			udpSocket = new DatagramSocket(serverPort+1);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		udpClients = new Vector<SocketAddress>();
		
	}
	
	public void startGame(Vector<PlayerDefinition> players) {
		this.players = players;
		new ServerUDPReceiver(udpSocket,udpClients,players).start();
		new ServerUDPSender(udpSocket,udpClients,players).start();
		System.out.println(players.size());
	}
	
	public void close() {
		chaser=0;
		udpClients.clear();
		udpSocket.disconnect();
		udpSocket.close();
		clientAdder.interrupt();
		return;
	}

	public class ServerUDPReceiver extends Thread {
		private DatagramSocket udpSock;
		private Vector<SocketAddress> udpClients;
		private Vector<PlayerDefinition> players;
		public ServerUDPReceiver(DatagramSocket udpSock,Vector<SocketAddress> udpClients, Vector<PlayerDefinition> players) {
			this.udpSock = udpSock;
			this.udpClients = udpClients;
			this.players = players;
		}
		
		public void run() {
			ProtocolParser parser = ProtocolParser.getInstance();
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
				
				String rawData = new String(rcvPacket.getData()).trim();
				System.out.println("getting: " + rawData);
				Protocol fromClient = parser.parse(rawData);
				if (fromClient.getProtocol()==Protocol.PROTOCOL_CLIENT) {
					ClientProtocol cP = (ClientProtocol) fromClient;
					for (PlayerDefinition p : players) {
						if (p.getId()==cP.getPlayerID()) {
							System.out.println("Updating " + p.getId() + " [" + cP.toString() + "]");
							p.updateX(cP.getX());
							p.updateY(cP.getY());
							p.updateRotation(cP.getRotation());
							break;
						}
					}
					
					PlayerDefinition chas = null;
					Circle chasC = null;
					for (PlayerDefinition p : players) {
						if (p.getId()==chaser) {
							chas = p;
							chasC = new Circle(chas.getX(),chas.getY(),32);
						}
					}
					
					if (chas==null) {
						continue;
					}
					
					for (PlayerDefinition p : players) {
						if (p.getId()!=chaser) {
							Circle c = new Circle(p.getX(),p.getY(),32);
							if (c.intersects(chasC)) {
								chaser=p.getId();
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public class ServerUDPSender extends Thread {
		private DatagramSocket udpSock;
		private Vector<SocketAddress> udpClients;
		private Vector<PlayerDefinition> players;
		private int SEQ;
		public ServerUDPSender(DatagramSocket udpSock,Vector<SocketAddress> udpClients, Vector<PlayerDefinition> players) {
			this.udpSock = udpSock;
			this.udpClients = udpClients;
			this.SEQ = 0;
			this.players = players;
		}
		public void run() {
			long lastSend = 0;
			
			Random rand = new Random();
			chaser = rand.nextInt(players.size())+1;
			while (true) {
				long now = System.currentTimeMillis();
				if ((now - lastSend)>=50) {
					ArrayList<PlayerDefinition> arrPlayers = new ArrayList<PlayerDefinition>(players);
					String debugP = new ServerProtocol(SEQ,arrPlayers,chaser).toString();
					byte[] sndTemp = debugP.getBytes();
					System.out.println("Sending updates: " + debugP);
					for (SocketAddress sa : udpClients) {
						try {
							DatagramPacket snd = new DatagramPacket(sndTemp,sndTemp.length,sa);
							udpSock.send(snd);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					SEQ++;
					lastSend = now;
				}
			}
		}
	}
}
