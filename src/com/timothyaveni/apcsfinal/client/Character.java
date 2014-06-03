package com.timothyaveni.apcsfinal.client;

public abstract class Player extends Entity {
	private int velocity;
	private int baseDamage;

	public Player(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc){
		super(name, location, height, width, strength,  intelligence, speed, loc);
	}

	public void setVelocity(int s){
		velocity = s;
	}
	
	public void setBaseDamage(int d){
		baseDamage = d;
	}
	
	
	public Location getLocation(){
		return this.loc;
	}

	public void characterMove(boolean[] keyboard){
		if(keyboard[0]){
			if(keyboard[2]);
			else{
				loc.setX(loc.getX() + velocity);
				loc.setDirection(3);
			}
		}
		else if(keyboard[1]){
			if(keyboard[3]);
			else
				loc.setY(loc.getY() + velocity);
				
		}
		else if(keyboard[2]){
			if(keyboard[0]);
			else
				loc.setX(loc.getX() - velocity);
		}
		else if(keyboard[3]){
			if(keyboard[1]);
			else
				loc.setY(loc.getY() - velocity);
		}
	}

	public abstract Entity attack();


}
