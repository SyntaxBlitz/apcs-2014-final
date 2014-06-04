package com.timothyaveni.apcsfinal.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.timothyaveni.apcsfinal.networking.EntityType;

public abstract class Entity {

	protected int id;
	protected Location loc;

	protected boolean inCombat;

	public Entity(int id, Location loc) {
		this.id = id;
		this.loc = loc;
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

	public BufferedImage getImage(long frame) {
		BufferedImage image = null;
		if (inCombat == false) {
			try {
				image = ImageIO.read(FileReader.getFileFromResourceString(getFileLocation()));
				image = image.getSubimage((int) ((frame % 4) * 32), loc.getDirection() * 48, this.getWidth(),
						this.getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				image = ImageIO.read(FileReader.getFileFromResourceString(getFileLocation()));
				image = image.getSubimage((int) ((frame % 2) * 32 + 128), loc.getDirection() * 48, this.getWidth(),
						this.getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return image;
	}

	public abstract EntityType getType();
}
