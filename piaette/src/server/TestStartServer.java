package server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sun.security.jgss.GSSCaller;

public class TestStartServer extends JFrame implements ActionListener, Observer {
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
	protected JLabel infoLabel1,msgLabel1;
	private GameServer gs;
	
	private JButton startServerButton, stopButton, startGameButton;

	private JList<String> pList;
	private DefaultListModel<String> pListModel;

	public TestStartServer() {
		this.height = 300;
		this.width = 300;
		ServerLobby.getMailBox().addObserver(this);
		initUI();
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

		infoLabel1 = new JLabel("Messages:");
		msgLabel1 = new JLabel(" msgLabel1");
		msgLabel1.setToolTipText("Here come da output");

		southPanel.add(sInfoLabel);
		southPanel.add(sMSGLabel);
		southPanel.add(infoLabel1);
		southPanel.add(msgLabel1);


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
			 
			 displayMessage("Attempting to start Server!");
			 GameServer gs = new GameServer("Kyckling");
			 
			 
		 }else if(e.getSource() == stopButton){
			 displayMessage("Pressed Stop!");
			 
		 }else if(e.getSource() == startGameButton){
			 displayMessage("Pressed StartGame!");
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
	/**
	 * Fetch product names from the database and display them in the name list.
	 */
	private void fillNameList(String newName) {
		pListModel.removeAllElements();
		displayMessage("fillNameList");
		ArrayList<String> nameArray = new ArrayList<String>();
		nameArray.add(newName);

		for (int i = 0; i < nameArray.size(); i++) {
			pListModel.addElement(nameArray.get(i));

		}
	}


	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TestStartServer ex = new TestStartServer();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {;
		ArrayList<ClientHandler> playerHanList = (LobbyMailBox.getClients());
		
		System.out.println("update");
		for( ClientHandler cH : playerHanList){
			fillNameList(cH.getPlayer().getName());
		}
				
		
	}

}