package com.timothyaveni.apcsfinal.server;

import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;

public class ArcanusEnemy extends Entity implements BossAI {
	private int baseDmg = 30;
	private int goldValue = 40;

	public ArcanusEnemy(int id, Location loc) {
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

		track = players.get(smallestIndex);
		
		
			if (Math.abs(track.getLocation().getX() - getLocation().getX()) <= 250
					|| Math.abs(track.getLocation().getY() - getLocation().getY()) <= 250) {
				if(this.getHP() < 1000)
					summonMinions();
				else
					projectileAttack();
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
	
	public void summonMinions(){ //This method adds enemy entities to the server list of invisible entities
		server.getInvisibleEntities().add(new GolemEnemy(Server.getNextEntityId(), null)); //I need to figure out how
		server.getInvisibleEntities().add(new GolemEnemy(Server.getNextEntityId(), null));
		server.getInvisibleEntities().add(new GolemEnemy(Server.getNextEntityId(), null)); 
		
	}
	
	public void projectileAttack(){ //This method creates new projectiles to send to the server
		
	}

	public Location getLocation() {
		return super.getLocation();
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
		return "Skeleton.png"; //Needs to be fixed
	}

	@Override
	public int getStrength() {
		return 300;
	}

	@Override
	public int getSpeed() {
		return 25;
	}

	@Override
	public int getIntelligence() {
		return 100;
	}

	@Override
	public EntityType getType() {
		return EntityType.ARCANUS_ENEMY;
	}

	@Override
	public int getVelocity() {
		return 10;
	}

	public int getGoldValue() {
		return goldValue;
	}

	@Override
	public int getMaxHP() {
		return 1500;
	}

}
