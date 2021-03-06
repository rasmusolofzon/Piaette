package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.geom.Circle;

import utilities.ClientProtocol;
import utilities.PlayerDefinition;
import utilities.Protocol;
import utilities.ProtocolParser;
import utilities.ServerProtocol;

public class GameServer {
	private ServerLobby clientAdder;
	private DatagramSocket udpSocket;
	private Vector<PlayerDefinition> alivePlayers;
	private Vector<SocketAddress> udpClients;
	private ServerUDPReceiver receive;
	private ServerUDPSender send;
	private long lastIntersect;
	private static final long TIMEOUT = 5000;
	public static int chaser = 0;

	public GameServer(int serverPort, String serverName) {

		clientAdder = new ServerLobby(serverPort);
		clientAdder.start();
		udpSocket = null;
		try {
			udpSocket = new DatagramSocket(serverPort + 1);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		udpClients = new Vector<SocketAddress>();

	}

	@SuppressWarnings("unchecked")
	public void startGame(Vector<PlayerDefinition> players) {

		this.alivePlayers = (Vector<PlayerDefinition>) players.clone();
		receive = new ServerUDPReceiver(udpSocket, udpClients, players);
		receive.start();
		send = new ServerUDPSender(udpSocket, udpClients, players);
		send.start();
		System.out.println(players.size());
	}

	public void close() {
		chaser = 0;
		udpClients.clear();
		udpSocket.disconnect();
		udpSocket.close();
		clientAdder.closeSocket();
		clientAdder.interrupt();
		return;
	}

	public class ServerUDPReceiver extends Thread {
		private DatagramSocket udpSock;
		private Vector<SocketAddress> udpClients;
		private Vector<PlayerDefinition> players;

		public ServerUDPReceiver(DatagramSocket udpSock,
				Vector<SocketAddress> udpClients,
				Vector<PlayerDefinition> players) {
			this.udpSock = udpSock;
			this.udpClients = udpClients;
			this.players = players;
		}

		public void run() {
			ProtocolParser parser = ProtocolParser.getInstance();
			long startTime = System.currentTimeMillis();
			while (true) {
				long now = System.currentTimeMillis();
				long elapsedTime = now - startTime;
				byte[] rcvBuffer = new byte[65000];
				DatagramPacket rcvPacket = new DatagramPacket(rcvBuffer,
						rcvBuffer.length);
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
				Protocol fromClient = parser.parse(rawData);
				if (fromClient.getProtocol() == Protocol.PROTOCOL_CLIENT) {
					ClientProtocol cP = (ClientProtocol) fromClient;
					for (PlayerDefinition p : players) {
						if (p.getId() == cP.getPlayerID()) {
							p.updateX(cP.getX());
							p.updateY(cP.getY());
							p.updateRotation(cP.getRotation());
							p.updateTimer(cP.getTimer());
							p.setHeartbeat(now);
							break;
						}
					}

					PlayerDefinition chas = null;
					Circle chasC = null;
					for (PlayerDefinition p : players) {
						if (p.getId() == chaser) {
							chas = p;
							chasC = new Circle(chas.getX(), chas.getY(), 32);
						}
					}

					if (chas == null) {
						continue;
					}

					/*
					 * WARNING! TESTED!
					 */
					if (elapsedTime > 5000 && chas.getTimer() >= 30000) {
						alivePlayers.remove(chas);
						// randomize new chaser
						Random rand = new Random();
						System.out.println("size of alive players "
								+ alivePlayers.size());
						for (PlayerDefinition pDef : alivePlayers) {
							System.out.println("\t" + pDef.getName());
						}
						if (alivePlayers.size() > 0) {
							chas = alivePlayers.get(rand.nextInt(alivePlayers
									.size()));
							chaser = chas.getId();
							System.out.println("named new chaser to be: "
									+ chas.getName());
							lastIntersect = System.currentTimeMillis();
						}
					}

					if (System.currentTimeMillis() - lastIntersect < 3000)
						continue;
					for (PlayerDefinition p : players) {
						if (p.getId() != chaser) {
							Circle c = new Circle(p.getX(), p.getY(), 32);
							if (c.intersects(chasC)) {
								lastIntersect = System.currentTimeMillis();
								chaser = p.getId();
								System.out.println("Collision! " + p.getId());
								break;
							}
						}
					}
				}

				Iterator<PlayerDefinition> itr = players.iterator();
				while (itr.hasNext()) {
					PlayerDefinition p = itr.next();
					if (now - p.getLastHeartbeat() > TIMEOUT) {
						p.updateTimer(500000);
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

		public ServerUDPSender(DatagramSocket udpSock,
				Vector<SocketAddress> udpClients,
				Vector<PlayerDefinition> players) {
			this.udpSock = udpSock;
			this.udpClients = udpClients;
			this.SEQ = 0;
			this.players = players;
		}

		public void run() {
			long lastSend = 0;

			Random rand = new Random();
			chaser = rand.nextInt(players.size()) + 1;
			while (true) {
				long now = System.currentTimeMillis();
				if ((now - lastSend) >= 25) {
					ArrayList<PlayerDefinition> arrPlayers = new ArrayList<PlayerDefinition>(
							players);
					String debugP = new ServerProtocol(SEQ, arrPlayers, chaser)
					.toString();
					byte[] sndTemp = debugP.getBytes();
					for (SocketAddress sa : udpClients) {
						try {
							if (!udpSock.isClosed()) {
								DatagramPacket snd = new DatagramPacket(
										sndTemp, sndTemp.length, sa);
								udpSock.send(snd);
							}
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
