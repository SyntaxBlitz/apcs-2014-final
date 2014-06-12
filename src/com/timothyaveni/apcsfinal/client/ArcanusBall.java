package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;

public class ArcanusBall extends MagicBall {

	public ArcanusBall(int entityId, Location loc) {
		super(entityId, loc, 150, 0);
	}

	@Override
	public int getVelocity() {
		return 25;
	}

	@Override
	public EntityType getType() {
		return EntityType.ARCANUS_BALL;
	}

}
