package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServerOffer extends Thread {

	private int msPort;
	private MulticastSocket ms;
	private int port;

	public ServerOffer(int port) {
		this.msPort = 4099;
		this.port = port;
	}
	
	@Override
	public void run(){
		try {
			setUpMulticastServer();
			
			while(true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(),0,dp.getLength());
				System.out.println("Received: "+s);
				if(s.startsWith("piaetteServerRequest")){
					System.out.println("Matched server request.");
					offer(dp);
				}
			}
			
		} catch(IOException e) {
			System.out.println("Exception:"+e);
		}
	}

	private void setUpMulticastServer() throws IOException {
		ms = new MulticastSocket(msPort);
		InetAddress ia = InetAddress.getByName("experiment.mcast.net"); //Change ip to different multicast group later?
		ms.joinGroup(ia);
	}
	
	private void offer(DatagramPacket dp) throws IOException {
		
		DatagramSocket datagramSocket = new DatagramSocket(port);
		
		String hostname = InetAddress.getLocalHost().getHostName();
		InetAddress returnAddress = dp.getAddress();
		int returnPort = dp.getPort();
		
		System.out.println("Offering server to "+returnAddress);
		
		byte[] sendByte = new byte[1024];
		sendByte = ("piaetteServerOffer: "+hostname).getBytes();
		DatagramPacket send = new DatagramPacket(sendByte,sendByte.length,returnAddress,returnPort);
		datagramSocket.send(send);
	}
	
}
