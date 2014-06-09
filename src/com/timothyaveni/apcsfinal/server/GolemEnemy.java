package com.timothyaveni.apcsfinal.server;

import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.client.Tank;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;

public class GolemEnemy extends Entity implements EnemyAI {
	private int baseDmg = 30;
	private int goldValue = 40;

	public GolemEnemy(int id, Location loc) {
		super(id, loc);
	}

	public void attack(Player track) { // Test
		Server.addPacketToQueue(new EntityDamagePacket(Server.getNextPacketId(), track.getId(), baseDmg + getStrength()));
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
		Player track = null;

		double smallestDistance = getLocation().getDistanceTo(players.get(0).getLocation());
		int smallestIndex = 0;

		for (int i = 0; i < players.size(); i++) {
			double thisDistance = getLocation().getDistanceTo(players.get(i).getLocation());
			

			if (thisDistance < smallestDistance) {
				smallestDistance = thisDistance;
				smallestIndex = i;
			}
		}

		for(Player p : players){
			if(p instanceof Tank){
				track = p;
				break;
			}
			else
				track = players.get(smallestIndex);
		}
		
		

		if(track != null){
		
			if (Math.abs(track.getLocation().getX() - getLocation().getX()) <= 32
					|| Math.abs(track.getLocation().getY() - getLocation().getY()) <= 48) {
				attack(track);
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
		else
			return;

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
		return "Skeleton.png";
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
		return 16;
	}

	public int getGoldValue() {
		return goldValue;
	}

	@Override
	public int getMaxHP() {
		return 30;
	}

}
