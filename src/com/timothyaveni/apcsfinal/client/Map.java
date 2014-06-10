package com.timothyaveni.apcsfinal.client;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.timothyaveni.apcsfinal.networking.WorldSectionID;

public class Map {
	private BufferedImage image = null; // image to be used in render method

	private int worldSectionId;

	private MapMetadata metadata;

	public Map(int worldSectionId) {
		this.worldSectionId = worldSectionId;
		String fileLocation = WorldSectionID.getMapNameFromID(worldSectionId);

		// assigns Image of the map background to the field
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString(fileLocation + ".png"));

			metadata = new MapMetadata(fileLocation, worldSectionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// convenient accessor method
	public BufferedImage getPic(Location playerLocation) {
		return image.getSubimage(playerLocation.getX() - Client.WIDTH / 2, playerLocation.getY() - Client.HEIGHT / 2,
				1024, 768);
	}

	public MapMetadata getMetadata() {
		return this.metadata;
	}

	public int getWorldSectionId() {
		return this.worldSectionId;
	}
}