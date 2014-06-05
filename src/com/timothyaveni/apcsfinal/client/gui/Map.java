package com.timothyaveni.apcsfinal.client.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.timothyaveni.apcsfinal.client.FileReader;
import com.timothyaveni.apcsfinal.client.Location;

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
	public BufferedImage getPic(Location playerLocation) {
		return image.getSubimage(playerLocation.getX() - MapCanvas.WIDTH / 2, playerLocation.getY() - MapCanvas.HEIGHT
				/ 2, 1024, 768);
	}
}