package com.timothyaveni.apcsfinal.server;

import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.networking.EntityType;

public class SkeletonEnemy extends Entity implements EnemyAI {
	public SkeletonEnemy(int id, Location loc) {
		super(id, loc);
	}

	public void attack() { // Test
		return;

	}

	public void move(int distance, int direction, String plane) {
		if (plane.equals("X")) {
			if (direction > 0)
				this.loc.setX(this.loc.getX() + 16);
			else
				this.loc.setX(this.loc.getX() - 16);
		} else {
			if (direction > 0)
				this.loc.setY(this.loc.getY() + 16);
			else
				this.loc.setY(this.loc.getY() - 16);
		}

	}

	// Tracks player based off the player's location; might want all player
	// locations to determine closest?
	public void trackPlayer(ArrayList<Player> players) {
		if (players.size() == 0)
			return;
		Player track;

		double smallestDistance = getLocation().getDistanceTo(players.get(0).getLocation());
		int smallestIndex = 0;

		for (int i = 0; i < players.size(); i++) {
			double thisDistance = getLocation().getDistanceTo(players.get(i).getLocation());

			if (thisDistance < smallestDistance) {
				smallestDistance = thisDistance;
				smallestIndex = i;
			}
		}

		track = players.get(smallestIndex);

		if (Math.abs(track.getLocation().getX() - loc.getX()) <= 32
				|| Math.abs(track.getLocation().getY() - loc.getY()) <= 48) {
			// EntityDamagePacket(ServerThread.getNextPacketId(), track.getId(),
			// baseDmg + getSpeed());
			// attack
		} else if (track.getLocation().getX() - loc.getX() < track.getLocation().getY() - loc.getY()) {
			move((track.getLocation().getX() - loc.getX()), (loc.getX() - track.getLocation().getX()), "X");
		} else if (track.getLocation().getY() - loc.getY() < track.getLocation().getY() - loc.getY()) {
			move((track.getLocation().getY() - loc.getY()), (loc.getY() - track.getLocation().getY()), "Y");
		}

	}

	public Location getLocation() {
		return this.loc;
	}

	public Location getPlayerLocation(Location playerLoc) {
		return playerLoc; // This might not be needed.
	}

	@Override
	public int getHeight() {
		return 48;
	}

	@Override
	public int getWidth() {
		return 32;
	}

	@Override
	public String getFileLocation() {
		return "";
	}

	@Override
	public int getStrength() {
		return 6;
	}

	@Override
	public int getSpeed() {
		return 5;
	}

	@Override
	public int getIntelligence() {
		return 0;
	}

	@Override
	public EntityType getType() {
		return EntityType.SKELETON_ENEMY;
	}

	@Override
	public int getVelocity() {
		return 10;
	}

}
