package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.timothyaveni.apcsfinal.client.FileReader;

public class MenuButton extends JButton {

	private static final long serialVersionUID = 1L;
	private BufferedImage image, highlightedImage;

	public MenuButton(String fileLocation, String highlightedFileLocation) {

		// try-catch block assigns images from directory to fields
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString(fileLocation));
			highlightedImage = ImageIO.read(FileReader.getFileFromResourceString(highlightedFileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets what you see on button to the two images assigned above
		this.setIcon(new ImageIcon(image));
		this.setRolloverIcon(new ImageIcon(highlightedImage));

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
}