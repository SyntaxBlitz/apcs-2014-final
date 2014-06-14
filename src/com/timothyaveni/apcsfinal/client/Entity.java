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

	private boolean moving;
	private boolean startedAttack = false;

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

	public void setDirection(int d) {
		this.setLocation(new Location(getLocation().getX(), getLocation().getY(), d, this.getLocation()
				.getWorldSectionId()));
	}

	public void setHP(int hp) {
		this.hp = hp;
	}

	public int getHP() {
		return hp;
	}

	public BufferedImage getImage(long frame) {
		if (!moving) {
			if (startedAttack) {
				currentSubImage = image.getSubimage((int) (4 * getWidth()), getLocation().getDirection() * getHeight(),
						this.getWidth(), this.getHeight());
			} else {
				currentSubImage = image.getSubimage(0, getLocation().getDirection() * getHeight(), this.getWidth(),
						this.getHeight());
				currentFrameOffset = 1;
			}
			return currentSubImage;

		} else if (frame % 4 == 0 || this.getLocation().getDirection() != lastDirection) {
			currentSubImage = image.getSubimage((int) (currentFrameOffset * getWidth()), getLocation().getDirection()
					* getHeight(), this.getWidth(), this.getHeight());
			lastDirection = this.getLocation().getDirection();
			currentFrameOffset = (currentFrameOffset + 1) % 4;
		}/*
		 * else if (!moving && isInCombat()) { currentSubImage =
		 * image.getSubimage((int) ((currentFrameOffset % 2) * 32 + 128),
		 * getLocation() .getDirection() * 48, this.getWidth(),
		 * this.getHeight()); }
		 */
		return currentSubImage;
	}

	public abstract EntityType getType();

	protected boolean isMoving() {
		return moving;
	}

	protected void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isStartedAttack() {
		return startedAttack;
	}

	public void setStartedAttack(boolean startedAttack) {
		this.startedAttack = startedAttack;
	}
	
	public int getDirectionTowards(Entity e){
		
		int newDirection;
		Location loc = e.getLocation();
		
		if (loc.getX() == getLocation().getX() && loc.getY() > getLocation().getY())
			newDirection = 0;
		else if(loc.getX() == getLocation().getX() && loc.getY() > getLocation().getY())
			newDirection = 3;
		else if(loc.getY() == getLocation().getY() && loc.getX() > getLocation().getX())
			newDirection = 1;
		else if(loc.getY() == getLocation().getY() && loc.getX() < getLocation().getX())
			newDirection = 2;
		else
			newDirection = loc.getDirection();
		return newDirection;
	}
	
	public boolean isPlayer(Entity e){
		if (id == 0 ||id == 3 ||id == 4 ||id == 5 ||id == 6)
			return true;
		return false;
	}
}
