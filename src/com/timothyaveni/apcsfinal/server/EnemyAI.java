package com.timothyaveni.apcsfinal.server;

import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;

public interface EnemyAI {

	public void trackPlayer(Server server);

	public Location getLocation();

}
