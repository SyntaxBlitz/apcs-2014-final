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

	public void attack(Player track) {
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
	public void act(Server server) {
		ArrayList<Player> players = server.getPlayerList();
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

		if (Math.abs(track.getLocation().getX() - getLocation().getX()) <= 300
				|| Math.abs(track.getLocation().getY() - getLocation().getY()) <= 300) {
			if(server.getCurrentTick() % 150 == 0)
			{
				if (this.getHP() < 1000)
					summonMinions(server);
				else
					projectileAttack();
			}
		}
	}

	public void summonMinions(Server server) { // This method adds enemy entities to the server list of invisible entities
		
		GolemEnemy golem1 = new GolemEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		GolemEnemy golem2 = new GolemEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		SkeletonEnemy skelly1 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		SkeletonEnemy skelly2 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		SkeletonEnemy skelly3 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		SkeletonEnemy skelly4 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		SkeletonEnemy skelly5 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		SkeletonEnemy skelly6 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0,0,0,0));
		
		golem1.setLocation(new Location(this.getLocation().getX() + (this.getWidth() / 2 + golem1.getWidth() /2), this.getLocation().getY(), Location.EAST, this.getLocation().getWorldSectionId()));
		
		golem2.setLocation(new Location(this.getLocation().getX() - (this.getWidth() / 2 + golem1.getWidth() /2), this.getLocation().getY(), Location.WEST, this.getLocation().getWorldSectionId()));
		
		skelly1.setLocation(new Location(this.getLocation().getX() - (this.getWidth() / 2 + skelly1.getWidth() / 2), this.getLocation().getY() - (this.getHeight() / 2 + skelly1.getHeight() / 2), Location.WEST, this.getLocation().getWorldSectionId()));
		
		skelly2.setLocation(new Location(this.getLocation().getX(), this.getLocation().getY() - (this.getHeight() / 2 + skelly2.getHeight() / 2), Location.SOUTH, this.getLocation().getWorldSectionId()));
		
		skelly3.setLocation(new Location(this.getLocation().getX() + (this.getWidth() / 2 + skelly3.getWidth() / 2), this.getLocation().getY() - (this.getHeight() / 2 + skelly3.getHeight() / 2), Location.EAST, this.getLocation().getWorldSectionId()));
		
		skelly4.setLocation(new Location(this.getLocation().getX() - (this.getWidth() / 2 + skelly4.getWidth() / 2), this.getLocation().getY() + (this.getHeight() / 2 + skelly4.getHeight() / 2), Location.WEST, this.getLocation().getWorldSectionId()));
		
		skelly5.setLocation(new Location(this.getLocation().getX(), this.getLocation().getY() + (this.getHeight() / 2 + skelly5.getHeight() / 2), Location.SOUTH, this.getLocation().getWorldSectionId()));
		
		skelly6.setLocation(new Location(this.getLocation().getX() + (this.getWidth() / 2 + skelly6.getWidth() / 2), this.getLocation().getY() + (this.getHeight() / 2 + skelly6.getHeight() / 2), Location.EAST, this.getLocation().getWorldSectionId()));

		
		
		server.getInvisibleEntityList().add(golem1);
		server.getInvisibleEntityList().add(golem2);
		
		server.getInvisibleEntityList().add(skelly1);
		server.getInvisibleEntityList().add(skelly2);
		server.getInvisibleEntityList().add(skelly3);
		server.getInvisibleEntityList().add(skelly4);
		server.getInvisibleEntityList().add(skelly5);
		server.getInvisibleEntityList().add(skelly6);

	}

	public void projectileAttack() { // This method creates new projectiles to send to the server
		
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
		return "Arcanus.png"; // Needs to be fixed
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
