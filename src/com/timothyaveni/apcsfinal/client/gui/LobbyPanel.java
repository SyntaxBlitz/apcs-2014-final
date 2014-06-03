package com.timothyaveni.apcsfinal.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.FileReader;

/* This class is what where the players will group up until the game
 * is launched. There are many features that are not necessary, 
 * but add user friendly interactions. */

public class LobbyPanel extends JPanel implements ActionListener, UsesClient {

	private static final long serialVersionUID = 1L;

	private BufferedImage menuBackground;
	private GameFrame frame;
	private String characterName;
	private int numberOfPlayers;
	
	private Client client;

	private MenuButton pickTank, pickHealer, pickArcher, pickMage, pickRogue, quitLobby, startGame, kickPlayer;

	// JComponent elements that get added into the panel
	private JTextArea chatHistory, playerList;
	private JLabel characterView, listHeader;
	private JTextField chatInput;
	private JScrollPane chatScroll;

	public LobbyPanel(GameFrame frame, String characterName) {

		// to make sure JPanel is set-up correctly
		super();

		// takes the frame from the parameter and puts it in the field
		this.frame = frame;
		this.characterName = characterName;
		numberOfPlayers = 1;
		// sets text of the box that stores the players currently in the lobby
		playerList = new JTextArea(this.characterName);

		// this creates what will be the panel that will show the player what
		// class they currently have selected
		characterView = new JLabel("This will be where the selected class will be viewed");

		// this is the header for the playerList
		listHeader = new JLabel("Players " + numberOfPlayers + "/4");

		// this is the text box that the player types in to send chat
		chatInput = new JTextField("Insert text here", 30);

		// this will be where the text from the text box will be displayed
		chatHistory = new JTextArea();

		// this creates the buttons incorporated in the panel
		pickTank = new MenuButton("PickTankButton.png", "PickTankButtonHighlighted.png");
		pickHealer = new MenuButton("PickHealerButton.png", "PickHealerButtonHighlighted.png");
		pickArcher = new MenuButton("PickArcherButton.png", "PickArcherButtonHighlighted.png");
		pickMage = new MenuButton("PickMageButton.png", "PickMageButtonHighlighted.png");
		pickRogue = new MenuButton("PickRogueButton.png", "PickRogueButtonHighlighted.png");
		quitLobby = new MenuButton("QuitLobbyButton.png", "QuitLobbyButtonHighlighted.png");
		startGame = new MenuButton("StartGameButton.png", "StartGameButtonHighlighted.png");
		kickPlayer = new MenuButton("KickPlayerButton.png", "KickPlayerButtonHighlighted.png");

		// assigns the Background Image of the Panel
		try {
			menuBackground = ImageIO.read(FileReader.getFileFromResourceString("MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets layout for the buttons
		this.setLayout(null);

		// sets the sizes and coordinates of each object
		characterView.setBounds(444, 32, 256, 256);
		playerList.setBounds(32, 48, 128, 144);
		listHeader.setBounds(32, 32, 128, 32);
		chatInput.setBounds(320, 732, 672, 32);

		// sets it so you cannot edit either JTextArea element and sets wraps
		// the lines
		chatHistory.setEditable(false);
		chatHistory.setLineWrap(true);
		playerList.setEditable(false);
		playerList.setLineWrap(true);

		// sets the different colors and text of the JLabels incorporated in the
		// frame
		// setOpaque() is used so you can see the text
		characterView.setBackground(Color.BLACK);
		characterView.setOpaque(true);

		listHeader.setBackground(Color.BLACK);
		listHeader.setOpaque(true);
		listHeader.setForeground(Color.WHITE);
		listHeader.setVerticalAlignment(SwingConstants.TOP);
		listHeader.setHorizontalAlignment(SwingConstants.LEFT);

		// more setting of size and coordinates, this time the buttons
		pickTank.setBounds(196, 32, 128, 32);
		pickHealer.setBounds(196, 64, 128, 32);
		pickArcher.setBounds(196, 96, 128, 32);
		pickMage.setBounds(196, 128, 128, 32);
		pickRogue.setBounds(196, 160, 128, 32);
		startGame.setBounds(0, 572, 256, 64);
		kickPlayer.setBounds(0, 636, 256, 64);
		quitLobby.setBounds(0, 700, 256, 64);

		// assigns the chatScroll to the pane 'chatHistory' (see above)
		// sets size and location
		chatScroll = new JScrollPane(chatHistory);
		chatScroll.setBounds(320, 572, 672, 160);

		// adding of the actionListener to all the objects that need it
		chatInput.addActionListener(this);
		pickTank.addActionListener(this);
		pickHealer.addActionListener(this);
		pickArcher.addActionListener(this);
		pickMage.addActionListener(this);
		pickRogue.addActionListener(this);
		startGame.addActionListener(this);
		kickPlayer.addActionListener(this);
		quitLobby.addActionListener(this);

		// adds all elements to the panel
		this.add(characterView);
		this.add(playerList);
		this.add(listHeader);
		this.add(chatInput);
		this.add(chatScroll, BorderLayout.CENTER);

		this.add(pickTank);
		this.add(pickHealer);
		this.add(pickArcher);
		this.add(pickMage);
		this.add(pickRogue);
		this.add(startGame);
		this.add(kickPlayer);
		this.add(quitLobby);
	}

	// paints background to the JPanel
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(menuBackground, 0, 0, 1024, 768, null);
	}

	// makes sure the JPanel is the correct size
	@Override
	public Dimension getPreferredSize() {
		if (menuBackground == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(menuBackground.getWidth(), menuBackground.getHeight());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quitLobby) {
			frame.close();
			frame.changeFrame(new MenuPanel(frame));
		} else if (e.getSource() == startGame) {
			// JOptionPane.showMessageDialog(frame, "Standy by for beaming....",
			// "Start Game", JOptionPane.INFORMATION_MESSAGE);
			frame.close();
			frame.changeFrame(new MapCanvas());
		} else if (e.getSource() == kickPlayer) {
			String kick = JOptionPane.showInputDialog(frame, "Please enter player to kick");
			// send kick to the server so player is removed
		} else if (e.getSource() == chatInput) {
			if (chatInput.getText().length() >= 1) {
				chatHistory.append(chatInput.getText() + "\n");
				chatInput.setText("");
			}
		}
	}

	@Override
	public void setClient(Client client) {
		this.client = client;
	}
}