package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.client.*;

public interface EnemyAI {

	public Location trackPlayer(Location playerLoc);

	public Location getLocation();

	public Location getPlayerLocation();

}
