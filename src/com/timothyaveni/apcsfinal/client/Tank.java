package com.timothyaveni.apcsfinal.client;

public class Tank extends Player {

	public Tank(int id, Location loc) {
		super(id, loc);
		setVelocity(8);
	}

	public void attack() {
		return;
	}

	@Override
	public int getHeight() {
		return 48;
	}

	@Override
	public int getWidth() {
		return 32;
	}

	@Override
	public String getFileLocation() {
		return "Tank.png";
	}

	@Override
	public int getStrength() {
		return 10 + getLevel() * 5;
	}

	@Override
	public int getSpeed() {
		return 5 + getLevel() * 2;
	}

	@Override
	public int getIntelligence() {
		return 3 + getLevel();
	}

	@Override
	public int getBaseDamage() {
		return 12;
	}
	
}
