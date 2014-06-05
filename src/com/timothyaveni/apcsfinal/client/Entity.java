package com.timothyaveni.apcsfinal.client;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.timothyaveni.apcsfinal.networking.EntityType;

public abstract class Entity {

	protected int id;
	protected Location loc;
	private BufferedImage image;
	private BufferedImage currentSubImage;

	protected boolean inCombat;

	public Entity(int id, Location loc) {
		this.id = id;
		this.loc = loc;
		
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString(getFileLocation()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract int getHeight();

	public abstract int getWidth();

	public abstract String getFileLocation();

	public abstract int getStrength();

	public abstract int getSpeed();

	public abstract int getIntelligence();

	public abstract int getVelocity();

	public int getId() {
		return this.id;
	}

	public Location getLocation() {
		return this.loc;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public BufferedImage getImage(long frame) {
		if (frame % 5 == 0) {

			if (inCombat == false) {
				currentSubImage = image.getSubimage((int) ((frame % 4) * 32), loc.getDirection() * 48, this.getWidth(),
						this.getHeight());
			} else {
				currentSubImage = image.getSubimage((int) ((frame % 2) * 32 + 128), loc.getDirection() * 48,
						this.getWidth(), this.getHeight());
			}
		}
		return currentSubImage;
	}

	public abstract EntityType getType();
}
