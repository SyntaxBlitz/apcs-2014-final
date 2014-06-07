package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class Mage extends Player {

	public Mage(int id, Location loc) {
		super(id, loc);
		setHP(75);
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
	}

	@Override
	public int getBaseDamage() {
		return 35;
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
		return "Mage.png";
	}

	@Override
	public int getStrength() {
		return 5 + getLevel();
	}

	@Override
	public int getSpeed() {
		return 5 + getLevel();
	}

	@Override
	public int getIntelligence() {
		return 10 + getLevel() * 5;
	}

	@Override
	public int getVelocity() {
		return 6;
	}

	@Override
	public EntityType getType() {
		return EntityType.MAGE;
	}

}
