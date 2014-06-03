package com.timothyaveni.apcsfinal.client;

public abstract class Player extends Entity {
	private int velocity;
	private int baseDamage;
	private int level;

	public Player(int id, Location loc){
		super(id, loc);
	}

	public void setVelocity(int s){
		velocity = s;
	}
	
	public void setBaseDamage(int d){
		baseDamage = d;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public abstract void attack();


}
