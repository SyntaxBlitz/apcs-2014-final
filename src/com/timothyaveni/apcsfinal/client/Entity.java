package com.timothyaveni.apcsfinal.client;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.timothyaveni.apcsfinal.networking.EntityType;

public abstract class Entity {

	private int id;
	private Location loc;
	private BufferedImage image;
	private BufferedImage currentSubImage;
	private int lastDirection;
	private int currentFrameOffset;
	
	private int hp;

	private boolean inCombat;
	private boolean moving;

	public Entity(int id, Location loc) {
		this.id = id;
		this.setLocation(loc);
		lastDirection = loc.getDirection();
		currentFrameOffset = 0;

		this.hp = getMaxHP();
		
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
	
	public abstract int getMaxHP();

	public int getId() {
		return this.id;
	}

	public Location getLocation() {
		return this.loc;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public void setHP(int hp) {
		this.hp = hp;
	}

	public int getHP() {
		return hp;
	}
	
	public BufferedImage getImage(long frame) {
		if (!moving) {
			currentSubImage = image
					.getSubimage(0, getLocation().getDirection() * 48, this.getWidth(), this.getHeight());
			currentFrameOffset = 1;
			return currentSubImage;

		} else if (frame % 4 == 0 || this.getLocation().getDirection() != lastDirection) {

			// if (isInCombat() == false) {
			currentSubImage = image.getSubimage((int) (currentFrameOffset * 32), getLocation().getDirection() * 48,
					this.getWidth(), this.getHeight());
			/*
			 * } else { currentSubImage = image.getSubimage((int) ((currentFrameOffset % 2) * 32 + 128), getLocation() .getDirection() * 48, this.getWidth(),
			 * this.getHeight()); }
			 */
			lastDirection = this.getLocation().getDirection();
			currentFrameOffset = (currentFrameOffset + 1) % 4;
		} else if (!moving && isInCombat()) {
			currentSubImage = image.getSubimage((int) ((currentFrameOffset % 2) * 32 + 128), getLocation()
					.getDirection() * 48, this.getWidth(), this.getHeight());
		}
		return currentSubImage;
	}

	public abstract EntityType getType();

	protected boolean isMoving() {
		return moving;
	}

	protected void setMoving(boolean moving) {
		this.moving = moving;
	}

	protected boolean isInCombat() {
		return inCombat;
	}

	protected void setInCombat(boolean inCombat) {
		this.inCombat = inCombat;
	}

}
