package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.FileReader;

public class MenuPanel extends JPanel implements ActionListener, UsesClient {

	private static final long serialVersionUID = 1L;
	private JButton joinServer, options, exit;
	private BufferedImage menuBackground;
	private GameFrame frame;
	private Client client;

	public MenuPanel(GameFrame frame) {

		// to make sure JPanel is set-up correctly
		super();

		this.frame = frame;

		// constructs buttons for inside the Panel
		joinServer = new MenuButton("JoinServerButton.png", "JoinServerButtonHighlighted.png");
		options = new MenuButton("OptionButton.png", "OptionButtonHighlighted.png");
		exit = new MenuButton("ExitButton.png", "ExitButtonHighlighted.png");

		// assigns the Background Image of the Panel
		try {
			menuBackground = ImageIO.read(FileReader.getFileFromResourceString("MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets layout for the buttons
		this.setLayout(null);

		// sets size and location of buttons
		joinServer.setBounds(0, 572, 256, 64);
		options.setBounds(0, 636, 256, 64);
		exit.setBounds(0, 700, 256, 64);

		joinServer.addActionListener(this);
		options.addActionListener(this);
		exit.addActionListener(this);

		// adds buttons into the Panel
		this.add(joinServer);
		this.add(options);
		this.add(exit);
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

		if (e.getSource() == joinServer) {
			String serverIP = JOptionPane.showInputDialog(frame, "Enter Server IP:");

			while (serverIP == null || serverIP.equals("")) {
				if (serverIP == null) {
					return;
				}
				JOptionPane.showMessageDialog(frame, "Not a valid Server IP");
				serverIP = JOptionPane.showInputDialog(frame, "Enter Server IP:");
			}
			String characterName = JOptionPane.showInputDialog(frame, "Enter Character Name:");
			while (characterName == null || characterName.equals("")) {
				if (characterName == null) {
					return;
				}
				JOptionPane.showMessageDialog(frame, "Not a valid name");
				characterName = JOptionPane.showInputDialog(frame, "Enter Character Name:");
			}
			frame.close();
			frame.changeFrame(new LobbyPanel(frame, characterName));
		} else if (e.getSource() == options) {
			JOptionPane.showMessageDialog(frame, "Sorry, not implemented yet", "Options",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == exit) {
			frame.close();
			System.exit(0);
		}
	}

	@Override
	public void setClient(Client client) {
		this.client = client;
	}
}
