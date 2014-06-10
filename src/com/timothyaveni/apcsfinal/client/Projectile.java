package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;

public abstract class Projectile extends Entity {

	private int distanceTravelled;
	private int id;

	public Projectile(int id, Location loc) {
		super(id, loc);
		this.setDistanceTravelled(0);
	}

	public abstract int getMaxDistance();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public void update(Client client) {
		Location currentLocation = getLocation();
		Location newLocation = null;
		if (distanceTravelled + getVelocity() > getMaxDistance()) {
			newLocation = new Location(0, 0, 0, 0);
		} else {
			switch (currentLocation.getDirection()) {
			// TODO: make projectiles collide with things
				case Location.NORTH:
					if (client.getCurrentMap().isPointValid(currentLocation.getX(),
							currentLocation.getY() - getVelocity() - getWidth() / 2)) {
						newLocation = new Location(currentLocation.getX(), currentLocation.getY() - getVelocity(),
								currentLocation.getDirection(), currentLocation.getWorldSectionId());
					} else {
						newLocation = new Location(0, 0, 0, 0);
					}
					break;
				case Location.SOUTH:
					if (client.getCurrentMap().isPointValid(currentLocation.getX(),
							currentLocation.getY() + getVelocity() + getHeight() / 2)) {
						newLocation = new Location(currentLocation.getX(), currentLocation.getY() + getVelocity(),
								currentLocation.getDirection(), currentLocation.getWorldSectionId());
					} else {
						newLocation = new Location(0, 0, 0, 0);
					}
					break;
				case Location.EAST:
					if (client.getCurrentMap().isPointValid(currentLocation.getX() + getVelocity() + getWidth() / 2,
							currentLocation.getY())) {
						newLocation = new Location(currentLocation.getX() + getVelocity(), currentLocation.getY(),
								currentLocation.getDirection(), currentLocation.getWorldSectionId());
					} else {
						newLocation = new Location(0, 0, 0, 0);
					}
					break;
				case Location.WEST:
					if (client.getCurrentMap().isPointValid(currentLocation.getX() - getVelocity() - getWidth() / 2,
							currentLocation.getY())) {
						newLocation = new Location(currentLocation.getX() - getVelocity(), currentLocation.getY(),
								currentLocation.getDirection(), currentLocation.getWorldSectionId());
					} else {
						newLocation = new Location(0, 0, 0, 0);
					}
					break;
			}
		}
		setLocation(newLocation);
		client.getNetworkThread().sendPacket(
				new EntityLocationPacket(Client.getNextPacketId(), getId(), newLocation));
		if (newLocation.equals(new Location(0, 0, 0, 0))) {
			client.getMyProjectiles().remove(this);
		} else {
			distanceTravelled += getVelocity();
		}
	}

}
