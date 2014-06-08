package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class Healer extends Player {

	public Healer(int id, Location loc) {
		super(id, loc);
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
	}

	@Override
	public int getBaseDamage() {
		return 10;
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
		return "Healer.png";
	}

	@Override
	public int getStrength() {
		return 15 + getLevel() * 2;
	}

	@Override
	public int getSpeed() {
		return 5 + getLevel();
	}

	@Override
	public int getIntelligence() {
		return 15 + getLevel() * 3;
	}

	@Override
	public int getVelocity() {
		return 6;
	}

	@Override
	public int getMaxHP() {
		return 75;
	}

	@Override
	public EntityType getType() {
		return EntityType.HEALER;
	}

}
