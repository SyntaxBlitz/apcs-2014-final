package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class Rogue extends Player {

	public Rogue(int id, Location loc) {
		super(id, loc);
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

		return "rogue.png";
	}

	@Override
	public int getStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIntelligence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getAttackRadius() {
		return this.getHeight();
	}

}
