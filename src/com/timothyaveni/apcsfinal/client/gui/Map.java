package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.FileReader;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;
	String imageFileName; // name of the file location
	private BufferedImage image = null; // image to be used in render method

	public Map(String fileLocation) { // file location does nothing as of yet
										// for testing purposes

		imageFileName = fileLocation;

		// assigns Image of the map background to the field
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString(fileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// creates background with JPanel
		JLabel pic = new JLabel(new ImageIcon(image));
		add(pic);

	}

	// makes sure image is drawn correctly
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, 1024, 768, null);
	}

	// correctly returns the preferred size of the JPanel
	public Dimension getPreferredSize() {
		if (image == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(image.getWidth(), image.getHeight());
		}
	}

	// convenient accessor method
	public BufferedImage getPic() {
		return image;
	}
}