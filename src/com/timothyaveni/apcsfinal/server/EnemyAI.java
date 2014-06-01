package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.client.*;

public interface EnemyAI {

	public EntityDamagePacket trackPlayer(Location playerLoc);

	public Location getLocation();

	public Location getPlayerLocation(Location playerLoc);

}
