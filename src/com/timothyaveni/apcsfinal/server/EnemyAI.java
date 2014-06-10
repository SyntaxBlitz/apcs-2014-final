package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.client.Location;

public interface EnemyAI {

	public void act(Server server);

	public Location getLocation();

}
