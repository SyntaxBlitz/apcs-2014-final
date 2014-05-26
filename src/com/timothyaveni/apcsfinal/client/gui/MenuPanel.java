package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton joinServer, options, exit;
	private BufferedImage menuBackground;

	public MenuPanel() {
		super();
		joinServer = new MenuButton("E:\\JoinServerButton.png",
				"E:\\JoinServerButtonHighlighted.png");
		options = new MenuButton("E:\\OptionButton.png",
				"E:\\OptionButtonHighlighted.png");
		exit = new MenuButton("E:\\ExitButton.png",
				"E:\\ExitButtonHighlighted.png");
		try {
			menuBackground = ImageIO.read(new File("E:\\MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setLayout(new FlowLayout());

		this.add(joinServer);
		this.add(options);
		this.add(exit);
		// joinServer.setLocation(0, 572);

		joinServer.addActionListener(this);
		options.addActionListener(this);
		exit.addActionListener(this);
	}

	// @Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joinServer)
			joinServer.setIcon(joinServer.getSelectedIcon());
		else if (e.getSource() == options)
			options.setIcon(options.getSelectedIcon());
		else if (e.getSource() == exit)
			exit.setIcon(exit.getSelectedIcon());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(menuBackground, 0, 0, 1024, 768, null);
	}

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
