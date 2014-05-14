package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientGUI implements ActionListener {
	public static void main(String[] args) {
		new ClientGUI();
	}
	
	public ClientGUI() {
		init();
	}
	private JTextField tHost,tPort,tPlayer;
	private JLabel lMessage,lTopicHost,lTopicPort,lTopicName;
	private JButton bJoin;
	
	private void init() {
		JPanel north = new JPanel();
		north.setLayout(new BorderLayout());
		
		JPanel northTop = new JPanel();
		northTop.setLayout(new FlowLayout());
		JPanel northCenter = new JPanel();
		northCenter.setLayout(new FlowLayout());
		JPanel northBottom = new JPanel();
		northBottom.setLayout(new FlowLayout());
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		
		lTopicHost = new JLabel("Server address:");
		lTopicPort = new JLabel("Server port:");
		lTopicName = new JLabel("Player name:");
		lMessage = new JLabel("\r\n\r\n\r\n");
		
		tHost = new JTextField(10);
		tPort = new JTextField(4);
		tPlayer = new JTextField(8);
		
		tHost.setText("localhost");
		tPort.setText("22222");
		tPlayer.setText("Blä");
		
		bJoin = new JButton("Join game");
		bJoin.addActionListener(this);
		
		JFrame f = new JFrame("Join game");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		
		northTop.add(lTopicName);
		northTop.add(tPlayer);
		northCenter.add(lTopicHost);
		northCenter.add(tHost);
		northCenter.add(lTopicPort);
		northCenter.add(tPort);
		northBottom.add(bJoin);
		
		
		north.add(northTop,BorderLayout.NORTH);
		north.add(northCenter,BorderLayout.CENTER);
		north.add(northBottom,BorderLayout.SOUTH);

		f.add(lMessage,BorderLayout.CENTER);

		f.add(north,BorderLayout.NORTH);
		f.add(south,BorderLayout.SOUTH);
		
		f.pack();
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		enableAll(false);
		
		String host = tHost.getText();
		String player = tPlayer.getText();
		
		int port = 0;
		try {
			port = Integer.parseInt(tPort.getText());
		}catch(Exception e) {
			enableAll(true);
			lMessage.setText("You must specify a valid port number");
			return;
		}
		
		try {
			enableAll(false);
			new LobbyClient(host,port,player);
			lMessage.setText("Waiting for game to start");
		}catch(Exception e) {
			lMessage.setText("Could not connect to server: " + e.getMessage() + " on " + host + ":" + port);
			enableAll(true);
			return;
		}
	}
	
	private void enableAll(boolean flag) {
		tHost.setEnabled(flag);
		tPort.setEnabled(flag);
		bJoin.setEnabled(flag);
		tPlayer.setEnabled(flag);
	}
}
