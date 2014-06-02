package com.timothyaveni.apcsfinal.client;

public abstract class Character extends Entity {


	public Character(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc){
		super(name, location, height, width, strength,  intelligence, speed, loc);
	}

	public void move(int distance, int direction, String plane){}
	
	public Location getLocation(){
		return this.loc;
	}

	public void characterMove(boolean[] keyboard){
		if(keyboard[0]){
			if(keyboard[2]);
			else
				loc.setX(loc.getX() + 8);
		}
		else if(keyboard[1]){
			if(keyboard[3]);
			else
				loc.setY(loc.getY() + 8);
		}
		else if(keyboard[2]){
			if(keyboard[0]);
			else
				loc.setX(loc.getX() - 8);
		}
		else if(keyboard[3]){
			if(keyboard[1]);
			else
				loc.setY(loc.getY() - 8);
		}
	}

	public abstract Entity attack();


}
