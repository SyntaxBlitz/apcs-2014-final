package com.timothyaveni.apcsfinal.client;

import java.util.HashMap;
import java.util.Iterator;

public abstract class Player extends Entity {

	private int level;

	public Player(int id, Location loc) {
		super(id, loc);
		level = 1; // this may need to be changed depending on how players are
					// sent in packets
	}

	public void move(int distance, int direction, String plane) {
	}

	// welcome to the ugliest code in the project.

	public void characterMove(boolean[] keyboard, Map currentMap, HashMap<Integer, Entity> entityList) {
		if (keyboard[0]) { // right
			if (!keyboard[2])
				if (currentMap
						.isPointValid(getLocation().getX() + getVelocity() + getWidth() / 2, getLocation().getY())
						&& !playerCollidesWithEntity(getLocation().getX() + getVelocity(), getLocation().getY(),
								currentMap, entityList))
					setLocation(new Location(getLocation().getX() + getVelocity(), getLocation().getY(), Location.EAST,
							getLocation().getWorldSectionId()));
				else
					setLocation(getClosestValidLocation(Location.EAST, currentMap, entityList));
		} else if (keyboard[1]) { // down
			if (!keyboard[3])
				if (currentMap.isPointValid(getLocation().getX(), getLocation().getY() + getVelocity() + getHeight()
						/ 2)
						&& !playerCollidesWithEntity(getLocation().getX(), getLocation().getY() + getVelocity(),
								currentMap, entityList))
					setLocation(new Location(getLocation().getX(), getLocation().getY() + getVelocity(),
							Location.SOUTH, getLocation().getWorldSectionId()));
				else
					setLocation(getClosestValidLocation(Location.SOUTH, currentMap, entityList));
		} else if (keyboard[2]) { // left
			if (currentMap.isPointValid(getLocation().getX() - getVelocity() - getWidth() / 2, getLocation().getY())
					&& !playerCollidesWithEntity(getLocation().getX() - getVelocity(), getLocation().getY(),
							currentMap, entityList))
				setLocation(new Location(getLocation().getX() - getVelocity(), getLocation().getY(), Location.WEST,
						getLocation().getWorldSectionId()));
			else
				setLocation(getClosestValidLocation(Location.WEST, currentMap, entityList));
		} else if (keyboard[3]) { // up
			if (currentMap.isPointValid(getLocation().getX(), getLocation().getY() - getVelocity() - getHeight() / 2)
					&& !playerCollidesWithEntity(getLocation().getX(), getLocation().getY() - getVelocity(),
							currentMap, entityList))
				setLocation(new Location(getLocation().getX(), getLocation().getY() - getVelocity(), Location.NORTH,
						getLocation().getWorldSectionId()));
			else
				setLocation(getClosestValidLocation(Location.NORTH, currentMap, entityList));
		}
	}

	private Location getClosestValidLocation(int direction, Map currentMap, HashMap<Integer, Entity> entityList) {
		if (direction == Location.NORTH) {
			int good = getLocation().getY() - getHeight() / 2;
			int bad = getLocation().getY() - getHeight() / 2 - getVelocity();
			int mid = (good + bad) / 2;
			while (bad != mid) {
				if (currentMap.isPointValid(getLocation().getX(), mid)
						&& !playerCollidesWithEntity(getLocation().getX(), mid + getHeight() / 2, currentMap,
								entityList)) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(getLocation().getX(), good + getHeight() / 2, direction, currentMap.getWorldSectionId());
		} else if (direction == Location.SOUTH) {
			int good = getLocation().getY() + getHeight() / 2;
			int bad = getLocation().getY() + getHeight() / 2 + getVelocity();
			int mid = (good + bad) / 2;
			while (good != mid) {
				if (currentMap.isPointValid(getLocation().getX(), mid)
						&& !playerCollidesWithEntity(getLocation().getX(), mid - getHeight() / 2, currentMap,
								entityList)) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(getLocation().getX(), good - getHeight() / 2, direction, currentMap.getWorldSectionId());
		} else if (direction == Location.EAST) {
			int good = getLocation().getX() + getWidth() / 2;
			int bad = getLocation().getX() + getWidth() / 2 + getVelocity();
			int mid = (good + bad) / 2;
			while (good != mid) {
				if (currentMap.isPointValid(mid, getLocation().getY())
						&& !playerCollidesWithEntity(mid - getWidth() / 2, getLocation().getY(), currentMap, entityList)) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(good - getWidth() / 2, getLocation().getY(), direction, currentMap.getWorldSectionId());
		} else { // WEST
			int good = getLocation().getX() - getWidth() / 2;
			int bad = getLocation().getX() - getWidth() / 2 - getVelocity();
			int mid = (good + bad) / 2;
			while (bad != mid) {
				if (currentMap.isPointValid(mid, getLocation().getY())
						&& !playerCollidesWithEntity(mid + getWidth() / 2, getLocation().getY(), currentMap, entityList)) {
					good = mid;
				} else {
					bad = mid;
				}
				mid = (good + bad) / 2;
			}
			return new Location(good + getWidth() / 2, getLocation().getY(), direction, currentMap.getWorldSectionId());
		}
	}

	private boolean playerCollidesWithEntity(int centerX, int centerY, Map currentMap,
			HashMap<Integer, Entity> entityList) {
		Iterator<Entity> i = entityList.values().iterator();
		while (i.hasNext()) {
			Entity entity = i.next();
			if (entity == this)
				continue;
			Location myLocation = getLocation();
			Location entityLocation = entity.getLocation();
			if (entityLocation.getWorldSectionId() == myLocation.getWorldSectionId()) {
				// brace for rectangle overlap code *shudder*
				if (centerX - getWidth() / 2 < entityLocation.getX() + entity.getWidth() / 2
						&& centerX + getWidth() / 2 > entityLocation.getX() - entity.getWidth() / 2
						&& centerY - getHeight() / 2 < entityLocation.getY() + entity.getHeight() / 2
						&& centerY + getHeight() / 2 > entityLocation.getY() - entity.getHeight() / 2) {
					return true;
				}
			}
		}
		return false;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public abstract double getAttackRadius();

	public abstract int getBaseDamage();

	public abstract void attack(Client client);
	
	public abstract void useAbility();
}
