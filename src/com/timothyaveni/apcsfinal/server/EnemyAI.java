package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.client.*;

public interface EnemyAI {

	public Location trackPlayer();

	public int attack();

	public Location getLocation();

	public Location getPlayerLocation();

}
