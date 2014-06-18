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

	public void characterMove(boolean[] keyboard, Map currentMap, java.util.Map<Integer, Entity> entities) {
		if (keyboard[0]) { // right
			if (!keyboard[2])
				if (currentMap.getMetadata().isPointValid(getLocation().getX() + getVelocity() + getWidth() / 2,
						getLocation().getY())
						&& !playerCollidesWithEntity(getLocation().getX() + getVelocity(), getLocation().getY(),
								currentMap, entities))
					setLocation(new Location(getLocation().getX() + getVelocity(), getLocation().getY(), Location.EAST,
							getLocation().getWorldSectionId()));
				else
					setLocation(getClosestValidLocation(Location.EAST, currentMap, entities));
		} else if (keyboard[1]) { // down
			if (!keyboard[3])
				if (currentMap.getMetadata().isPointValid(getLocation().getX(),
						getLocation().getY() + getVelocity() + getHeight() / 2)
						&& !playerCollidesWithEntity(getLocation().getX(), getLocation().getY() + getVelocity(),
								currentMap, entities))
					setLocation(new Location(getLocation().getX(), getLocation().getY() + getVelocity(),
							Location.SOUTH, getLocation().getWorldSectionId()));
				else
					setLocation(getClosestValidLocation(Location.SOUTH, currentMap, entities));
		} else if (keyboard[2]) { // left
			if (currentMap.getMetadata().isPointValid(getLocation().getX() - getVelocity() - getWidth() / 2,
					getLocation().getY())
					&& !playerCollidesWithEntity(getLocation().getX() - getVelocity(), getLocation().getY(),
							currentMap, entities))
				setLocation(new Location(getLocation().getX() - getVelocity(), getLocation().getY(), Location.WEST,
						getLocation().getWorldSectionId()));
			else
				setLocation(getClosestValidLocation(Location.WEST, currentMap, entities));
		} else if (keyboard[3]) { // up
			if (currentMap.getMetadata().isPointValid(getLocation().getX(),
					getLocation().getY() - getVelocity() - getHeight() / 2)
					&& !playerCollidesWithEntity(getLocation().getX(), getLocation().getY() - getVelocity(),
							currentMap, entities))
				setLocation(new Location(getLocation().getX(), getLocation().getY() - getVelocity(), Location.NORTH,
						getLocation().getWorldSectionId()));
			else
				setLocation(getClosestValidLocation(Location.NORTH, currentMap, entities));
		}
	}

	private Location getClosestValidLocation(int direction, Map currentMap, java.util.Map<Integer, Entity> entities) {
		if (direction == Location.NORTH) {
			int good = getLocation().getY() - getHeight() / 2;
			int bad = getLocation().getY() - getHeight() / 2 - getVelocity();
			int mid = (good + bad) / 2;
			while (bad != mid) {
				if (currentMap.getMetadata().isPointValid(getLocation().getX(), mid)
						&& !playerCollidesWithEntity(getLocation().getX(), mid + getHeight() / 2, currentMap, entities)) {
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
				if (currentMap.getMetadata().isPointValid(getLocation().getX(), mid)
						&& !playerCollidesWithEntity(getLocation().getX(), mid - getHeight() / 2, currentMap, entities)) {
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
				if (currentMap.getMetadata().isPointValid(mid, getLocation().getY())
						&& !playerCollidesWithEntity(mid - getWidth() / 2, getLocation().getY(), currentMap, entities)) {
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
				if (currentMap.getMetadata().isPointValid(mid, getLocation().getY())
						&& !playerCollidesWithEntity(mid + getWidth() / 2, getLocation().getY(), currentMap, entities)) {
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
			java.util.Map<Integer, Entity> entities2) {
		Entity[] entities = entities2.values().toArray(new Entity[0]);
		for (Entity entity : entities) {
			if (entity == this || entity instanceof Projectile)
				continue;
			Location myLocation = getLocation();
			Location entityLocation = entity.getLocation();
			if (entity instanceof Player && myLocation.getDistanceTo(currentMap.getMetadata().getSpawnPoint()) < 200)
				continue; // if we're close to spawn, phase through other players
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

	public int getDamageNumber() {
		return getBaseDamage() + ((int) (Math.random() * getDamageVariance() * 2 - getDamageVariance()));
	}

	public abstract double getAttackRadius();

	public abstract int getBaseDamage();

	public abstract int getDamageVariance();

	public abstract void attack(Client client);

	public abstract void useAbility(long frame, Client client);

	public abstract void updateAbility(long frame);

}
