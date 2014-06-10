package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class Arrow extends Projectile {

	public Arrow(int id, Location loc) {
		super(-1, loc);
		setId(id);
	}

	@Override
	public int getMaxDistance() {
		return 1000;
	}

	@Override
	public int getBaseDamage() {
		return 25;
	}

	@Override
	public int getHeight() {
		return 32;
	}

	@Override
	public int getWidth() {
		return 32;
	}

	@Override
	public String getFileLocation() {
		return "Goblin.png";	//TODO
	}

	@Override
	public int getStrength() {
		return -1;
	}

	@Override
	public int getSpeed() {
		return -1;
	}

	@Override
	public int getIntelligence() {
		return -1;
	}

	@Override
	public int getVelocity() {
		return 16;
	}

	@Override
	public int getMaxHP() {
		return -1;
	}

	@Override
	public EntityType getType() {
		return EntityType.ARROW;
	}

}
