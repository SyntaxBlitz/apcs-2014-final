package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuButton extends JButton {

	private static final long serialVersionUID = 1L;
	private BufferedImage image, highlightedImage;

	public MenuButton(String fileLocation, String highlightedFileLocation) {

		try {
			image = ImageIO.read(new File(fileLocation));
			highlightedImage = ImageIO.read(new File(highlightedFileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setIcon(new ImageIcon(image));
		this.setRolloverIcon(new ImageIcon(highlightedImage));

	}

	@Override
	public ImageIcon getSelectedIcon() {
		return new ImageIcon(highlightedImage);
	}

	@Override
	public Dimension getPreferredSize() {
		if (image == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(image.getWidth(), image.getHeight());
		}
	}
}