package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class Arrow extends Projectile {

	private int damage;
	
	public Arrow(int id, Location loc, int damage) {
		super(-1, loc);
		setId(id);
		this.damage = damage;
	}

	@Override
	public int getMaxDistance() {
		return 1000;
	}

	@Override
	public int getBaseDamage() {
		return this.damage;
	}

	@Override
	public int getHeight() {
		return 24;
	}

	@Override
	public int getWidth() {
		return 24;
	}

	@Override
	public String getFileLocation() {
		return "Arrow.png";
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
