package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class Tank extends Player {

	public Tank(int id, Location loc) {
		super(id, loc);
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

	@Override
	public EntityType getType() {
		return EntityType.TANK;
	}

	@Override
	public int getMaxHP() {
		return 250;
	}

	@Override
	public int getVelocity() {
		return 4;
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
	}

}
