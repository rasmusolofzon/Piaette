package Client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ClientDiscover {
	/*public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("Invalid argument. Valid argument is: 'java ClientDiscover <port>'");
			System.exit(0);
		}
		
		String command = args[1];
		int port = 0;
		
		try {
			port = Integer.parseInt(args[0]);
		}
		catch(Exception e) {
			port = 0;
		}
		
		if (port==0) {
			System.out.println("Invalid argument. Valid argument is: 'java ClientDiscover <port>'");
			System.exit(0);
		}
	}*/
	
	private int port;
	private ArrayList<String> serverList;
	
	public ClientDiscover (int port) {
		this.port = port;
		serverList = new ArrayList<String>();
	}
	
	private String discoverServer() {
		String rVal = null;
		try {
			MulticastSocket ms = new MulticastSocket();
			ms.setTimeToLive(1);
			
			InetAddress addr = InetAddress.getByName("experiment.mcast.net");
			
			
			String DISC_HEAD = "DISCOVER_GAME";
			byte[] snd = DISC_HEAD.getBytes();
			byte[] rcv = new byte[2048];
			
			DatagramPacket send = new DatagramPacket(snd,snd.length,addr,4099);
			ms.send(send);
			
			DatagramPacket recieve = new DatagramPacket(rcv,rcv.length);
			
			ms.receive(recieve);
			
			String hostname = new String(recieve.getData()).trim();
			if (hostname.length()>0) {
				serverList.add(hostname);
				rVal = hostname;
			}
			
			ms.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rVal;
	}
	
	public void send() {
		String host = this.discoverServer();
		if (host==null) {
			return;
		}
		
		//new ClientDiscover(host, port).send();
	}
	
	
	
}
