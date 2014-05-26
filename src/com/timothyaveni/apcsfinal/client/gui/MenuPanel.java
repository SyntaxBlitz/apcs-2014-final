package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton joinServer, options, exit;
	private BufferedImage menuBackground;

	public MenuPanel() {

		// to make sure JPanel is set-up correctly
		super();

		// constructs buttons for inside the Panel
		joinServer = new MenuButton("E:\\JoinServerButton.png",
				"E:\\JoinServerButtonHighlighted.png");
		options = new MenuButton("E:\\OptionButton.png",
				"E:\\OptionButtonHighlighted.png");
		exit = new MenuButton("E:\\ExitButton.png",
				"E:\\ExitButtonHighlighted.png");

		// assigns the Background Image of the Panel
		try {
			menuBackground = ImageIO.read(new File("E:\\MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets layout for the buttons
		this.setLayout(new FlowLayout());

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
			return new Dimension(menuBackground.getWidth(),
					menuBackground.getHeight());
		}
	}
}
