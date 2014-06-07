package com.timothyaveni.apcsfinal.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.timothyaveni.apcsfinal.client.gui.Map;

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

	public void characterMove(boolean[] keyboard, Map currentMap) {
		if (!isInCombat()) {
			if (keyboard[0]) { // right
				if (!keyboard[2])
					if (currentMap.isPointValid(getLocation().getX() + getVelocity() + getWidth() / 2, getLocation().getY()))
						setLocation(new Location(getLocation().getX() + getVelocity(), getLocation().getY(),
								Location.EAST));
					else
						setLocation(getClosestValidLocation(Location.EAST, currentMap));
			} else if (keyboard[1]) { // down
				if (!keyboard[3])
					if (currentMap.isPointValid(getLocation().getX(), getLocation().getY() + getVelocity() + getHeight() / 2))
						setLocation(new Location(getLocation().getX(), getLocation().getY() + getVelocity(),
								Location.SOUTH));
					else
						setLocation(getClosestValidLocation(Location.SOUTH, currentMap));
			} else if (keyboard[2]) { // left
				if (currentMap.isPointValid(getLocation().getX() - getVelocity() - getWidth() / 2, getLocation().getY()))
					setLocation(new Location(getLocation().getX() - getVelocity(), getLocation().getY(), Location.WEST));
				else
					setLocation(getClosestValidLocation(Location.WEST, currentMap));
			} else if (keyboard[3]) { // up
				if (currentMap.isPointValid(getLocation().getX(), getLocation().getY() - getVelocity() - getHeight() / 2))
					setLocation(new Location(getLocation().getX(), getLocation().getY() - getVelocity(), Location.NORTH));
				else
					setLocation(getClosestValidLocation(Location.NORTH, currentMap));
			}
		} else if (keyboard[0] || keyboard[1] || keyboard[2] || keyboard[3])
			setInCombat(false);
	}

	private Location getClosestValidLocation(int direction, Map currentMap) {
		System.out.println("call");
		if (direction == Location.NORTH) {
			int good = getLocation().getY() - getHeight() / 2;
			int bad = getLocation().getY() - getHeight() / 2 - getVelocity();
			int mid = (good + bad) / 2;
			while (bad != mid) {
				if (currentMap.isPointValid(getLocation().getX(), mid)) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(getLocation().getX(), good + getHeight() / 2, direction);
		} else if (direction == Location.SOUTH) {
			int good = getLocation().getY() + getHeight() / 2;
			int bad = getLocation().getY() + getHeight() / 2 + getVelocity();
			int mid = (good + bad) / 2;
			while (good != mid) {
				if (currentMap.isPointValid(getLocation().getX(), mid)) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(getLocation().getX(), good - getHeight() / 2, direction);
		} else if (direction == Location.EAST) {
			int good = getLocation().getX() + getWidth() / 2;
			int bad = getLocation().getX() + getWidth() / 2 + getVelocity();
			int mid = (good + bad) / 2;
			while (good != mid) {
				if (currentMap.isPointValid(mid, getLocation().getY())) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(good - getWidth() / 2, getLocation().getY(), direction);
		} else {	// WEST
			int good = getLocation().getX() - getWidth() / 2;
			int bad = getLocation().getX() - getWidth() / 2 - getVelocity();
			int mid = (good + bad) / 2;
			while (bad != mid) {
				if (currentMap.isPointValid(mid, getLocation().getY())) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(good + getWidth() / 2, getLocation().getY(), direction);
		}
	}

	public void attack(HashMap<Integer, Entity> entities, boolean inCombat) {
		Location enemyLoc;
		if (this.isInCombat()) {
			for (Integer i : entities.keySet()) {
				Entity a = entities.get(i);
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
