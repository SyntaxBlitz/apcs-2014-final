package com.timothyaveni.apcsfinal.client;
package com.timothyaveni.apcsfinal.client;

public abstract class Player extends Entity {
	private int velocity;
	private int level;

	public Player(int id, Location loc) {
		super(id, loc);
	}

	public void setVelocity(int s) {
		velocity = s;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Location getLocation() {
		return this.loc;
	}

	public void characterMove(boolean[] keyboard) {
		if (keyboard[0]) {
			if (keyboard[2])
				;
			else {
				loc.setX(loc.getX() + velocity);
				loc.setDirection(3);
			}
		} else if (keyboard[1]) {
			if (keyboard[3])
				;
			else{
				loc.setY(loc.getY() + velocity);
				loc.setDirection(1);
			}
		} else if (keyboard[2]) {
			if (keyboard[0])
				;
			else{
				loc.setX(loc.getX() - velocity);
				loc.setDirection(2);	
			}
		} else if (keyboard[3]) {
			if (keyboard[1])
				;
			else{
				loc.setY(loc.getY() - velocity);
				loc.setDirection(4);
			}
		}
	}

	public void attack(ArrayList<Entity> entities){
		int height = this.getHeight;
		int width = this.getWidth;
		Location[] attackLocs = new Location[height * width / 2];
		switch(loc.getDirection){
		case 1:
			for (int p = 0; p < attackLocs.length; p ++){
				if((p + 1) % width <= width / 2){
					attackLocs[p] = new Location(loc.getX() -(width / 2 - p), loc.getY() + (p / width + 1));
				}
				else
					attackLocs[p] = new Location(loc.getX() + (p - width / 2), loc.getY() + (p / width + 1));
			}
			for (Entity a  : entities){
				for (Location p: attackLocs){
					if(a.getLocation().equals(p)){
						//notify Tim and return
					}
				}
			}
			break;
		case 2:
			for (int p = 0; p < attackLocs.length; p ++){
				if((p + 1) % height <= height / 2){
					attackLocs[p] = new Location(loc.getX() - (p / height + 1), loc.getY() + (height / 2 - p);
				}
				else
					attackLocs[p] = new Location(loc.getX() - (p / height + 1), loc.getY() - (p - height / 2));
 			}
			for (Entity a  : entities){
				for (Location p: attackLocs){
					if(a.getLocation().equals(p)){
						//notify Tim and return
					}
				}
			}
			break;
			
		}
	}

	public abstract int getBaseDamage();

}

