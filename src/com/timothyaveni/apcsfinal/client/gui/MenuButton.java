package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage image, highlightedImage;

	public MenuButton(String fileLocation, String highlightedFileLocation) {

		// try-catch block assigns images from directory to fields
		try {
			image = ImageIO.read(new File(fileLocation));
			highlightedImage = ImageIO.read(new File(highlightedFileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets what you see on button to the two images assigned above
		this.setIcon(new ImageIcon(image));
		this.setRolloverIcon(new ImageIcon(highlightedImage));

		this.addActionListener(this);
	}

	// Returns highlightedImage for actionPerformed event in MenuPanel class
	@Override
	public ImageIcon getSelectedIcon() {
		return new ImageIcon(highlightedImage);
	}

	// makes the button the correct size on the frame
	@Override
	public Dimension getPreferredSize() {
		if (image == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(image.getWidth(), image.getHeight());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this)
			this.setIcon(new ImageIcon(highlightedImage));

	}
}