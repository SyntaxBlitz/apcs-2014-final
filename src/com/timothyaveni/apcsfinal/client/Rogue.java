package com.timothyaveni.apcsfinal.client;

import java.util.HashMap;

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

		return "Rogue.png";
	}

	@Override
	public int getStrength() {
		return 10 + getLevel();
	}

	@Override
	public int getSpeed() {
		return 10 + getLevel();
	}

	@Override
	public int getIntelligence() {
		return 5 + getLevel();
	}

	@Override
	public int getVelocity() {
		return 8;
	}

	@Override
	public int getMaxHP() {
		return 50;
	}

	@Override
	public EntityType getType() {
		return EntityType.ROGUE;
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
	}

	@Override
	public void attack(Client client) {
		// TODO Auto-generated method stub
		
	}
	
}
