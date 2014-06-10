package com.timothyaveni.apcsfinal.client;

public class ArcanusBall extends MagicBall {
	
	private int baseDmg;
	private int velocity;
	
	public ArcanusBall(int entityId, Location loc, int damage, int velocity){
		super(entityId, loc);
		baseDmg = damage;
		this.velocity = velocity;
	}
	
	public int getBaseDamage(){
		return this.baseDmg;
	}

}
