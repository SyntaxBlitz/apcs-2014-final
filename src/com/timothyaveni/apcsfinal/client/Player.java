package com.timothyaveni.apcsfinal.client;

import java.util.ArrayList;

public abstract class Player extends Entity {

	private int level;
	private int HP;

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
		if(!inCombat){
			if (keyboard[0]) {
				if (!keyboard[2])
					loc = new Location(loc.getX() + getVelocity(), loc.getY(), Location.EAST);
			} else if (keyboard[1]) {
				if (!keyboard[3])
					loc = new Location(loc.getX(), loc.getY() + getVelocity(), Location.SOUTH);
			} else if (keyboard[2]) {
				if (!keyboard[0])
					loc = new Location(loc.getX() - getVelocity(), loc.getY(), Location.WEST);	
			} else if (keyboard[3]) {
				if (!keyboard[1])
					loc = new Location(loc.getX(), loc.getY() - getVelocity(), Location.NORTH);
			}	
		} else if(keyboard[0] || keyboard[1] || keyboard[2] || keyboard[3])
			inCombat = false;
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

	public void setHP(int hp){
		this.HP = hp;
	}

	public abstract double getAttackRadius();

	public abstract int getBaseDamage();
}
