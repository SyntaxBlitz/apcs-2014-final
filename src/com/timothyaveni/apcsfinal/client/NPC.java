package com.timothyaveni.apcsfinal.client;

public abstract class NPC extends Entity {

	
	public NPC(String name, String location, int height, int width,
			int strength, int intelligence, int speed, Location loc){
		super(name, location, height, width, strength,  intelligence, speed, loc);
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	public void interract(){}

}
