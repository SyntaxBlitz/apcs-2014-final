package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class Archer extends Player {

	public Archer(int id, Location loc) {
		super(id, loc);
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
	}

	@Override
	public int getBaseDamage() {
		return 25;
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
		return "Archer.png";
	}

	@Override
	public int getStrength() {
		return 20 + getLevel() * 2;
	}

	@Override
	public int getSpeed() {
		return 10 + getLevel() * 4;
	}

	@Override
	public int getIntelligence() {
		return 5 + getLevel();
	}

	@Override
	public int getVelocity() {
		return 6;
	}

	@Override
	public int getMaxHP() {
		return 100;
	}

	@Override
	public EntityType getType() {
		return EntityType.ARCHER;
	}

}
