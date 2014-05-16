package server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utilties.PlayerDefinition;
import utilties.ServerProtocol;



public class ServerGUI extends JFrame implements ActionListener, Observer {
	int height, width;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A label which is intended to contain a message text.
	 */
	protected JLabel msgLabel;
	protected JLabel sInfoLabel, sMSGLabel;
	protected JLabel portLabel;
	protected JTextField portMSGField;
	private GameServer gs;
	private boolean running;
	
	private JButton startServerButton, stopButton, startGameButton;

	private JList<String> pList;
	private DefaultListModel<String> pListModel;
	private Vector<PlayerDefinition> players;

	public ServerGUI() {
		this.height = 300;
		this.width = 300;
		running = false;
		ServerLobby.getMailBox().addObserver(this);
		initUI();
		players = new Vector<PlayerDefinition>();
	}

	private void initUI() {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel northPanel = new JPanel(new GridLayout(2, 2));

		startServerButton = new JButton("Start server");
		startServerButton.setToolTipText("Starts da Server!");
		startServerButton.addActionListener(this);

		stopButton = new JButton("Stop");
		stopButton.setToolTipText("Stoppes dat Server!");
		stopButton.addActionListener(this);
		
		startGameButton = new JButton("Start game");
		startGameButton.setToolTipText("Starts da game!");
		startGameButton.addActionListener(this);
		

		
		msgLabel = new JLabel("Messages:");
		msgLabel = new JLabel(" msgLabel");
		
		JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		welcomePanel.add(startServerButton);
		welcomePanel.add(stopButton);
		welcomePanel.add(startGameButton);
		northPanel.add(msgLabel);
		northPanel.add(welcomePanel);
		
		JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pListModel = new DefaultListModel<String>();
		pList = new JList<String>(pListModel);
		pList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pList.setPrototypeCellValue("123456789012");
		pList.addListSelectionListener(new pSelectionListener());
		JScrollPane p1 = new JScrollPane(pList);
		
		middlePanel.add(p1);

		JPanel southPanel = new JPanel(new GridLayout(2, 2));

		sInfoLabel = new JLabel("Server IP:");
		sMSGLabel = new JLabel(" sILabel");
		sMSGLabel.setToolTipText("This is da server IP");

		portLabel = new JLabel("Server Port:");
		portMSGField = new JTextField("22222");
		portMSGField.setToolTipText("Here come da port");

		southPanel.add(sInfoLabel);
		southPanel.add(sMSGLabel);
		southPanel.add(portLabel);
		southPanel.add(portMSGField);


		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);

		frame.setTitle("Server");

	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(height,width);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		

	}
	 public void actionPerformed(ActionEvent e)
     {
		 if(e.getSource() == startServerButton){
			 if(!running){
				 displayMessage("Attempting to start Server!");
				 int port = Integer.parseInt(portMSGField.getText());
				 portMSGField.setEnabled(false);
			 gs = new GameServer(port,"Kyckling");
			 try {
				sMSGLabel.setText(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			 running = true;
			 }else{
				 displayMessage("Server is runnig around!");
				 return;
			 }

			 
			 
		 }else if(e.getSource() == stopButton){
			 displayMessage("Pressed Stop!");
			 portMSGField.setEnabled(true);
			 gs.close();
			 
			 
		 }else if(e.getSource() == startGameButton){
			 displayMessage("Pressed StartGame!");

			 for (ClientHandler ch : ServerLobby.getMailBox().getClients()) {
				 PlayerDefinition pd = ch.getPlayer();
				 pd.updateX(10*pd.getId());
				 pd.updateY(10*pd.getId());
				 players.add(pd);				 
			 }
			 			 
			 Random rand = new Random();
			 int start = rand.nextInt(players.size())+1;
			 
			 ServerProtocol initProtocol = new ServerProtocol(0,new ArrayList<PlayerDefinition>(players),start);
			 
			 for (ClientHandler ch : ServerLobby.getMailBox().getClients()) {
				 try {
					ch.sendMessage("startGame");
					ch.sendMessage(initProtocol.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			 }
			 gs.startGame(players);
		 }
		
     }

	/**
	 * Display a message.
	 * 
	 * @param msg
	 *            The message to display.
	 */
	public void displayMessage(String msg) {
		msgLabel.setText(msg);
	}

	/**
	 * Clear the message line.
	 */
	public void clearMessage() {
		msgLabel.setText(" ");
	}

	//On�dig eftersom den v�ntar p� klickningar i listan...
	class pSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {

		}
	}



	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ServerGUI ex = new ServerGUI();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		pListModel.removeAllElements();
		ArrayList<ClientHandler> playerHanList = LobbyMailBox.getClients();
		for(ClientHandler cH : playerHanList){
			pListModel.addElement(cH.getPlayer().getName());
		}
				
		
	}

}
