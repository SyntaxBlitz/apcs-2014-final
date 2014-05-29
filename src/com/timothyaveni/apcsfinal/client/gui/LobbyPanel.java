package com.timothyaveni.apcsfinal.client.gui;

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
import javax.swing.JList;
import javax.swing.JPanel;

public class LobbyPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage menuBackground;
	private GameFrame frame;
	private JList list;
	private MenuButton pickTank, pickHealer, pickArcher, pickMage, pickRogue,
			quitLobby, startGame, kickPlayer;
	private JLabel characterView, listHeader;
	private String[] l = { "Timothy Aveni", "Chris Muller", "Nick Foster",
			"Dan Fisher" };

	public LobbyPanel(GameFrame frame) {

		// to make sure JPanel is set-up correctly
		super();

		this.frame = frame;

		list = new JList(l);
		characterView = new JLabel(
				"This will be where the selected \nclass will be viewed");
		listHeader = new JLabel("Players 4/4");

		listHeader.setOpaque(true);
		listHeader.setBackground(Color.WHITE);

		pickTank = new MenuButton("E:\\PickTankButton.png",
				"E:\\PickTankButtonHighlighted.png");
		pickHealer = new MenuButton("E:\\PickHealerButton.png",
				"E:\\PickHealerButtonHighlighted.png");
		pickArcher = new MenuButton("E:\\PickArcherButton.png",
				"E:\\PickArcherButtonHighlighted.png");
		pickMage = new MenuButton("E:\\PickMageButton.png",
				"E:\\PickMageButtonHighlighted.png");
		pickRogue = new MenuButton("E:\\PickRogueButton.png",
				"E:\\PickRogueButtonHighlighted.png");
		quitLobby = new MenuButton("E:\\QuitLobbyButton.png",
				"E:\\QuitLobbyButtonHighlighted.png");
		startGame = new MenuButton("E:\\StartGameButton.png",
				"E:\\StartGameButtonHighlighted.png");
		kickPlayer = new MenuButton("E:\\KickPlayerButton.png",
				"E:\\KickPlayerButtonHighlighted.png");

		// assigns the Background Image of the Panel
		try {
			menuBackground = ImageIO.read(new File("E:\\MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets layout for the buttons
		this.setLayout(null);

		characterView.setBounds(444, 256, 256, 256);
		list.setBounds(32, 48, 128, 144);
		listHeader.setBounds(32, 32, 128, 32);
		listHeader.setVerticalTextPosition(0);
		listHeader.setHorizontalTextPosition(0);

		pickTank.setBounds(196, 32, 128, 32);
		pickHealer.setBounds(196, 64, 128, 32);
		pickArcher.setBounds(196, 96, 128, 32);
		pickMage.setBounds(196, 128, 128, 32);
		pickRogue.setBounds(196, 160, 128, 32);
		startGame.setBounds(0, 572, 256, 64);
		kickPlayer.setBounds(0, 636, 256, 64);
		quitLobby.setBounds(0, 700, 256, 64);

		pickTank.addActionListener(this);
		pickHealer.addActionListener(this);
		pickArcher.addActionListener(this);
		pickMage.addActionListener(this);
		pickRogue.addActionListener(this);
		startGame.addActionListener(this);
		kickPlayer.addActionListener(this);
		quitLobby.addActionListener(this);

		this.add(characterView);
		this.add(list);
		this.add(listHeader);

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
			return new Dimension(menuBackground.getWidth(),
					menuBackground.getHeight());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}