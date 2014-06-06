package com.timothyaveni.apcsfinal.client.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.timothyaveni.apcsfinal.client.FileReader;
import com.timothyaveni.apcsfinal.client.Location;

public class Map {
	private BufferedImage image = null; // image to be used in render method
	private BufferedImage collision;

	public Map(String fileLocation, String ext) {

		// assigns Image of the map background to the field
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString(fileLocation + ext));
			collision = ImageIO.read(FileReader.getFileFromResourceString(fileLocation + "_Collision" + ext));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// convenient accessor method
	public BufferedImage getPic(Location playerLocation) {
		return image.getSubimage(playerLocation.getX() - MapCanvas.WIDTH / 2, playerLocation.getY() - MapCanvas.HEIGHT
				/ 2, 1024, 768);
	}

	public boolean isPointValid(int x, int y) {
		return (collision.getRGB(x, y) & 0xFFFFFF) == 0xFFFFFF;
	}

	public MapMetadata getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}
}