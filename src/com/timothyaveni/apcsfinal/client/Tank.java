package com.timothyaveni.apcsfinal.client;

public class Tank extends Player {
	
	
	public Tank(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc){
		super(name, location, height, width, strength,  intelligence, speed, loc);
		setVelocity(8);
		setBaseDamage(12);
	}

	

	@Override
	public Entity attack() {
		return null;
	}

}
