package com.timothyaveni.apcsfinal.client;

public abstract class Character extends Entity {
	
	
	public Character(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc){
		super(name, location, height, width, strength,  intelligence, speed, loc);
	}

	@Override
	public abstract void move();
	
	public abstract Entity attack();
	
	
	//just for testing push 
	

}
