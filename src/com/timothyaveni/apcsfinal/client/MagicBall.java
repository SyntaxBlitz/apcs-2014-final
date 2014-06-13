package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class MagicBall extends Projectile {

	public MagicBall(int entityId, Location loc, int damageAmount) {
		super(-1, loc, damageAmount);
		setId(entityId);
	}

	@Override
	public int getMaxDistance() {
		return 600;
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
		return "MagicBall.png";
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
		return 10;
	}

	@Override
	public int getMaxHP() {
		return -1;
	}

	@Override
	public EntityType getType() {
		return EntityType.MAGIC_BALL;
	}

}
