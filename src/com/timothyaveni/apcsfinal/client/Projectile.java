package com.timothyaveni.apcsfinal.client;

import java.awt.Rectangle;

import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
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

			Entity[] entities = client.getEntityList().values().toArray(new Entity[0]);
			for (int i = 0; i < entities.length; i++) {
				Entity thisEntity = entities[i];
				if (thisEntity == client.getPlayer() || thisEntity instanceof Projectile)	// doesn't collide with this player or other projectiles
					continue;
				// rectangle collision code provided by java.awt.Rectangle this time
				if (new Rectangle(thisEntity.getLocation().getX() - thisEntity.getWidth() / 2, thisEntity.getLocation()
						.getY() - thisEntity.getHeight() / 2, thisEntity.getWidth(), thisEntity.getHeight())
						.intersects(new Rectangle(getLocation().getX() - getWidth() / 2, getLocation().getY()
								- getHeight() / 2, getWidth(), getHeight()))) {
					newLocation = new Location(0, 0, 0, 0);	// get rid of the projectile because it collided with someone
					if (!(thisEntity instanceof Player)) {
						client.getNetworkThread().sendPacket(new EntityDamagePacket(Client.getNextPacketId(), thisEntity.getId(), getBaseDamage()));
					}
				}
			}
		}
		setLocation(newLocation);
		client.getNetworkThread().sendPacket(new EntityLocationPacket(Client.getNextPacketId(), getId(), newLocation));
		if (newLocation.equals(new Location(0, 0, 0, 0))) {
			client.getMyProjectiles().remove(this);
		} else {
			distanceTravelled += getVelocity();
		}
	}
	
	public abstract int getBaseDamage();

}
