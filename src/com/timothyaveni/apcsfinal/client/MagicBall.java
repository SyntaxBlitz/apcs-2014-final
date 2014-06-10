package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class MagicBall extends Projectile {

	public MagicBall(int entityId, Location loc) {
		super(-1, loc);
		setId(entityId);
	}

	@Override
	public int getMaxDistance() {
		return 600;
	}

	@Override
	public int getHeight() {
		return 32; // TODO: check this
	}

	@Override
	public int getWidth() {
		return 32; // TODO: check this
	}

	@Override
	public String getFileLocation() {
		return "Goblin.png"; // TODO: write this
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
		return 12;
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
