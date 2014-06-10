package com.timothyaveni.apcsfinal.client;

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

}
