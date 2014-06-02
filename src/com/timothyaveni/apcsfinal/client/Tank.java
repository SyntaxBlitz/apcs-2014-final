package com.timothyaveni.apcsfinal.client;

public class Tank extends Character {
	
	private final int BASE_DAMAGE = 10;
	
	public Tank(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc){
		super(name, location, height, width, strength,  intelligence, speed, loc);
	}

	@Override
	public void characterMove(boolean[] keyboard) {
		if(keyboard[0] == true){
			if(keyboard[1] == true);
			else
				loc.setX(loc.getX() + 16);
		}

	}

	@Override
	public Entity attack() {
		return null;
	}

}
