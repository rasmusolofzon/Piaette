package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import zframtidensMenu.MainMenu.PNGFileFilter;

public class ClientGUI implements ActionListener {
	public static void main(String[] args) {
		new ClientGUI();
	}

	public ClientGUI() {
		init();
	}

	private JTextField tHost, tPort, tPlayer;
	private JLabel lMessage, lTopicHost, lTopicPort, lTopicName, picLabel,
			sloganLabel;
	private JButton bJoin;
	private BufferedImage image, slogan;
	private FileFilter filter;

	private void init() {
		JPanel north = new JPanel();
		north.setLayout(new BorderLayout());

		tHost = new JTextField(10);
		tPort = new JTextField(4);
		tPlayer = new JTextField(8);
		tHost.setText("192.168.1.83");
		tPort.setText("22222");
		tPlayer.setText("Axelander");
		
		loadGraphics();

		JPanel northTop = new JPanel();
		northTop.setLayout(new FlowLayout());
		JPanel northCenter = new JPanel();
		northCenter.setLayout(new FlowLayout());
		JPanel northBottom = new JPanel();
		northBottom.setLayout(new FlowLayout());

		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());

		lTopicHost = new JLabel("Server address:");
		lTopicPort = new JLabel("Server port:");
		lTopicName = new JLabel("Player name:");
		lMessage = new JLabel("\r\n\r\n\r\n");





		bJoin = new JButton("Join game");
		bJoin.addActionListener(this);

		JFrame f = new JFrame("Join game");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());

		// north
		northTop.add(picLabel);
		northCenter.add(sloganLabel);
		northBottom.add(lTopicName);
		northBottom.add(tPlayer);
		northBottom.add(lTopicHost);
		northBottom.add(tHost);
		northBottom.add(lTopicPort);
		northBottom.add(tPort);

		north.add(northTop, BorderLayout.NORTH);
		north.add(northCenter, BorderLayout.CENTER);
		north.add(northBottom, BorderLayout.SOUTH);

		f.add(lMessage, BorderLayout.CENTER);
		f.add(north, BorderLayout.NORTH);

		// south
		south.add(bJoin);
		f.add(south, BorderLayout.SOUTH);

		f.pack();
		f.setVisible(true);
	}

	private void loadGraphics() {
		try {
			// Gametitle generator
			image = ImageIO.read(new File("Graphics/menu/GameTitle.png"));
			picLabel = new JLabel(new ImageIcon(image));

			// slogan generator
			Random generator = new Random();
			filter = new PNGFileFilter();
			int nbrOfSlogans = new File("Graphics/menu/slogan/")
					.listFiles(filter).length;
			int randomNbrOfSlogans = generator.nextInt(nbrOfSlogans) + 1;
			randomNbrOfSlogans =8;
			if (randomNbrOfSlogans == 8)
				loadMusic();
			slogan = ImageIO.read(new File("Graphics/menu/slogan/slogan-"
					+ randomNbrOfSlogans + ".png"));
			sloganLabel = new JLabel(new ImageIcon(slogan));

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void loadMusic() {
		try {
			tPlayer.setText("Vladislav");
			tPlayer.setEditable(false);
			String url = "sounds/clientMusic.wav";

			File yourFile = new File(url);
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			Clip clip;

			stream = AudioSystem.getAudioInputStream(yourFile);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.loop(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void actionPerformed(ActionEvent e) {
		enableAll(false);

		String host = tHost.getText();
		String player = tPlayer.getText();

		int port = 0;
		try {
			port = Integer.parseInt(tPort.getText());
		} catch (Exception ex) {
			enableAll(true);
			lMessage.setText("You must specify a valid port number");
			return;
		}

		try {
			new LobbyClient(host, port, player);
			lMessage.setText("Waiting for game to start");
		} catch (Exception ex) {
			lMessage.setText("Could not connect to server: " + ex.getMessage()
					+ " on " + host + ":" + port);
			enableAll(true);
			return;
		}

	}

	private void enableAll(boolean flag) {
		tHost.setEnabled(flag);
		tPort.setEnabled(flag);
		tPlayer.setEnabled(flag);
		bJoin.setEnabled(flag);
	}
}
