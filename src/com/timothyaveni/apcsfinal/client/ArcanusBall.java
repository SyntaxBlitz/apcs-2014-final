package com.timothyaveni.apcsfinal.client;

public class ArcanusBall extends MagicBall {
	
	public ArcanusBall(int entityId, Location loc){
		super(entityId, loc);
	}
	
	@Override
	public int getBaseDamage() {
		return 60;
	}
	
	@Override
	public int getVelocity() {
		return 20;
	}

}
