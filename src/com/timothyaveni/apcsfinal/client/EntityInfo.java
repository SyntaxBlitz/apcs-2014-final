package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class EntityInfo {

	private EntityType type;
	private Location location;

	public EntityInfo(EntityType type, Location location) {
		this.type = type;
		this.location = location;
	}

	public EntityType getType() {
		return this.type;
	}

	public Location getLocation() {
		return this.location;
	}

}
