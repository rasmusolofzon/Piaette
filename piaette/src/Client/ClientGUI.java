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
	private JTextField tHost,tPort;
	private JLabel lMessage,lTopicHost,lTopicPort;
	private JButton bJoin;
	
	private void init() {
		JPanel north = new JPanel();
		north.setLayout(new BorderLayout());
		
		JPanel northTop = new JPanel();
		northTop.setLayout(new FlowLayout());
		JPanel northBottom = new JPanel();
		northBottom.setLayout(new FlowLayout());
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		
		lTopicHost = new JLabel("Server address:");
		lTopicPort = new JLabel("Server port:");
		lMessage = new JLabel("\r\n\r\n\r\n");
		
		tHost = new JTextField(10);
		tPort = new JTextField(4);
		
		bJoin = new JButton("Join game");
		bJoin.addActionListener(this);
		
		JFrame f = new JFrame("Join game");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		
		northTop.add(lTopicHost);
		northTop.add(tHost);
		northTop.add(lTopicPort);
		northTop.add(tPort);
		northBottom.add(bJoin);
		
		
		
		north.add(northTop,BorderLayout.NORTH);
		f.add(lMessage,BorderLayout.CENTER);
		north.add(northBottom,BorderLayout.SOUTH);
		
		f.add(north,BorderLayout.NORTH);
		f.add(south,BorderLayout.SOUTH);
		
		f.pack();
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		enableAll(false);
		
		String host = tHost.getText();
		int port = 0;
		try {
			port = Integer.parseInt(tPort.getText());
		}catch(Exception e) {
			enableAll(true);
			lMessage.setText("You must specify a valid port number");
			return;
		}
		
		try {
			new LobbyClient(host,port);
			lMessage.setText("Waiting for game to start");
		}catch(Exception e) {
			lMessage.setText("Could not connect to server: " + e.getMessage());
			enableAll(true);
			return;
		}
	}
	
	private void enableAll(boolean flag) {
		tHost.setEnabled(flag);
		tPort.setEnabled(flag);
		bJoin.setEnabled(flag);
	}
}
