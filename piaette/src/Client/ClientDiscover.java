package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.Timer;

public class ClientDiscover implements ActionListener {
	private Timer tmr;
	private int seconds;
	public static void main(String[] args) {
		ClientDiscover cd = new ClientDiscover(4099);
		System.out.println(cd.discoverServers());
	}
		
	private int port;
	private ArrayList<String> serverList;
	
	public ClientDiscover (int port) {
		this.port = port;
		serverList = new ArrayList<String>();
		tmr = new Timer(1000,this);
		seconds = 0;
	}
	
	public ArrayList<String> discoverServers() {
		try {
			MulticastSocket ms = new MulticastSocket();
			ms.setTimeToLive(1);
			ms.setSoTimeout(5000);
			
			InetAddress addr = InetAddress.getByName("experiment.mcast.net");
			
			String DISC_HEAD = "piaetteServerRequest";
			byte[] snd = DISC_HEAD.getBytes();
			
			DatagramPacket send = new DatagramPacket(snd,snd.length,addr,4099);
			
			ms.send(send);
			tmr.start();
			
			do {
				String data = "neeeeej:n";
				while ((!data.startsWith("piaetteServerOffer"))) {
					byte[] rcv = new byte[2048];
					DatagramPacket recieve = new DatagramPacket(rcv,rcv.length);
					try {
						ms.receive(recieve);
					}catch (SocketTimeoutException e2) {
						tmr.stop();
						break;
					}
					data = new String(recieve.getData()).trim();
					System.out.println("Rcv: " + data);
				}
				
				String hostname = data.split(":")[1];
				
				if (hostname.length() > 0) {
					serverList.add(hostname);
				}
			} while (seconds < 5 && tmr.isRunning());
			
			tmr.stop();
			ms.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return serverList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(seconds++);
	}
}
