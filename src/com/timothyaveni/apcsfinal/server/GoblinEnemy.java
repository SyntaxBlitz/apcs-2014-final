package com.timothyaveni.apcsfinal.server;

import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;

public class GoblinEnemy extends Entity implements EnemyAI {
	private int baseDmg = 10;
	private int goldValue = 25;

	public GoblinEnemy(int id, Location loc) {
		super(id, loc);
	}

	public EntityDamagePacket attack(EntityDamagePacket e) { // Test
		return e;
	}

	public void move(int distance, int direction, String plane) {
		if (plane.equals("X")) {
			if (direction > 0)
				setLocation(new Location(this.getLocation().getX() + getVelocity(), this.getLocation().getY(),
						Location.SOUTH, this.getLocation().getWorldSectionId()));
			else
				setLocation(new Location(this.getLocation().getX() - getVelocity(), this.getLocation().getY(),
						Location.NORTH, this.getLocation().getWorldSectionId()));
		} else {
			if (direction > 0)
				setLocation(new Location(this.getLocation().getX(), this.getLocation().getY() + getVelocity(),
						Location.EAST, this.getLocation().getWorldSectionId()));
			else
				setLocation(new Location(this.getLocation().getX(), this.getLocation().getY() - getVelocity(),
						Location.WEST, this.getLocation().getWorldSectionId()));
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

		if (Math.abs(track.getLocation().getX() - getLocation().getX()) <= 32
				|| Math.abs(track.getLocation().getY() - getLocation().getY()) <= 48) {
			Server.addPacketToQueue(new EntityDamagePacket(Server.getNextPacketId(), track.getId(), baseDmg
					+ getSpeed()));
		} else if (track.getLocation().getX() - getLocation().getX() < track.getLocation().getY()
				- getLocation().getY()) {
			move((track.getLocation().getX() - getLocation().getX()), (getLocation().getX() - track.getLocation()
					.getX()), "X");
		} else if (track.getLocation().getY() - getLocation().getY() < track.getLocation().getY()
				- getLocation().getY()) {
			move((track.getLocation().getY() - getLocation().getY()), (getLocation().getY() - track.getLocation()
					.getY()), "Y");
		}

	}

	public Location getLocation() {
		return super.getLocation();
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
		return "Healer.png"; // TODO: change
	}

	@Override
	public int getStrength() {
		return 5;
	}

	@Override
	public int getSpeed() {
		return 10;
	}

	@Override
	public int getIntelligence() {
		return 0;
	}

	@Override
	public EntityType getType() {
		return EntityType.GOBLIN_ENEMY;
	}

	@Override
	public int getVelocity() {
		return 20;
	}

	public int getGoldValue() {
		return goldValue;
	}

	@Override
	public int getMaxHP() {
		return 25;
	}
}
