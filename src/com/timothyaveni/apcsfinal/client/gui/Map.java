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

public class Map {
	String imageFileName; // name of the file location
	private BufferedImage image = null; // image to be used in render method

	public Map(String fileLocation) {

		imageFileName = fileLocation;

		// assigns Image of the map background to the field
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString(fileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// convenient accessor method
	public BufferedImage getPic() {
		return image.getSubimage(0, 0, 1024, 768);	// this is just to test. Seems to be relatively quick.
	}
}