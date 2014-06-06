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

	public void characterMove(boolean[] keyboard) {
		if (!isInCombat()) {
			if (keyboard[0]) {
				if (!keyboard[2])
					setLocation(new Location(getLocation().getX() + getVelocity(), getLocation().getY(), Location.EAST));
			} else if (keyboard[1]) {
				if (!keyboard[3])
					setLocation(new Location(getLocation().getX(), getLocation().getY() + getVelocity(), Location.SOUTH));
			} else if (keyboard[2]) {
				if (!keyboard[0])
					setLocation(new Location(getLocation().getX() - getVelocity(), getLocation().getY(), Location.WEST));
			} else if (keyboard[3]) {
				if (!keyboard[1])
					setLocation(new Location(getLocation().getX(), getLocation().getY() - getVelocity(), Location.NORTH));
			}
		} else if (keyboard[0] || keyboard[1] || keyboard[2] || keyboard[3])
			setInCombat(false);
	}

	public void attack(ArrayList<Entity> entities, boolean inCombat) {
		Location enemyLoc;
		if(this.isInCombat()){
			for (Entity a : entities) {
				enemyLoc = a.getLocation();
				if (enemyLoc.getDistanceTo(getLocation()) <= getAttackRadius()) {
					switch (getLocation().getDirection()) {
						case Location.NORTH:
						case Location.SOUTH:
							if (Math.abs(getLocation().getX() - enemyLoc.getX()) <= a.getWidth()) {
								// notify server
							}
							break;
						case Location.EAST:
						case Location.WEST:
							if (Math.abs(getLocation().getY() - enemyLoc.getY()) <= a.getHeight()) {
								// notify server
							}
							break;
					}
	
				}
			}
		}

	}

	public int getLevel() {
		return level;
	}

	public void setHP(int hp) {
		this.HP = hp;
	}

	public abstract double getAttackRadius();

	public abstract int getBaseDamage();
}
