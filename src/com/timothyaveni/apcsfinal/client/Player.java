package com.timothyaveni.apcsfinal.client;

import java.util.ArrayList;

public abstract class Player extends Entity {

	private int level;

	public Player(int id, Location loc) {
		super(id, loc);
		level = 1; // this may need to be changed depending on how players are
					// sent in packets
	}

	public void move(int distance, int direction, String plane) {
	}

	public Location getLocation() {
		return this.loc;
	}

	public void characterMove(boolean[] keyboard) {
		if (keyboard[0]) {
			if (keyboard[2])
				;
			else{
				loc.setX(loc.getX() + getVelocity());
				loc.setDirection(Location.EAST);
			}	
		} else if (keyboard[1]) {
			if (keyboard[3])
				;
			else{
				loc.setY(loc.getY() + getVelocity());
				loc.setDirection(Location.NORTH);
			}	
		} else if (keyboard[2]) {
			if (keyboard[0])
				;
			else{
				loc.setX(loc.getX() - getVelocity());
				loc.setDirection(Location.WEST);
			}	
		} else if (keyboard[3]) {
			if (keyboard[1])
				;
			else{
				loc.setY(loc.getY() - getVelocity());
				loc.setDirection(Location.SOUTH);
			}	
		}
	}

	public void attack(ArrayList<Entity> entities) {
		Location enemyLoc;
		for (Entity a : entities) {
			enemyLoc = a.getLocation();
			if (enemyLoc.getDistanceTo(loc) <= getAttackRadius()) {
				switch (loc.getDirection()) {
					case 1:
					case 4:
						if (Math.abs(loc.getX() - enemyLoc.getX()) <= this.getWidth()) {
							// notify server
							return;
						}
						break;
					case 2:
					case 3:
						if (Math.abs(loc.getY() - enemyLoc.getY()) <= this.getHeight()) {
							// notify server
							return;
						}
						break;
				}

			}
		}

	}

	public int getLevel() {
		return level;
	}

	public abstract double getAttackRadius();

	public abstract int getBaseDamage();
}
