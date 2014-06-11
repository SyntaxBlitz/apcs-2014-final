package com.timothyaveni.apcsfinal.server;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.client.Tank;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;


public class GolemEnemy extends Entity implements EnemyAI {
	private int baseDmg = 50;
	private int goldValue = 40;

	public GolemEnemy(int id, Location loc) {
		super(id, loc);
	}

	public void attack(Player track) {
		Server.addPacketToQueue(new EntityDamagePacket(Server.getNextPacketId(), track.getId(), baseDmg + getStrength()));
	}

	public void move(int distance, int direction, String plane) {
		if (plane.equals("X")) {
			if (direction > 0) {
				Location newLocation = new Location(this.getLocation().getX() + distance, this.getLocation().getY(),
						Location.EAST, this.getLocation().getWorldSectionId());
				Server.addPacketToQueue(new EntityLocationPacket(Server.getNextPacketId(), this.getId(), newLocation));
				setLocation(newLocation);
			} else {
				Location newLocation = new Location(this.getLocation().getX() - distance, this.getLocation().getY(),
						Location.WEST, this.getLocation().getWorldSectionId());
				Server.addPacketToQueue(new EntityLocationPacket(Server.getNextPacketId(), this.getId(), newLocation));
				setLocation(newLocation);
			}
		} else {
			if (direction > 0) {
				Location newLocation = new Location(
						this.getLocation().getX(), this.getLocation().getY() + distance, Location.SOUTH, this
						.getLocation().getWorldSectionId());
				Server.addPacketToQueue(new EntityLocationPacket(Server.getNextPacketId(), this.getId(), newLocation));
				setLocation(newLocation);
			} else {
				Location newLocation = new Location(
						this.getLocation().getX(), this.getLocation().getY() - distance, Location.NORTH, this
						.getLocation().getWorldSectionId());
				Server.addPacketToQueue(new EntityLocationPacket(Server.getNextPacketId(), this.getId(), newLocation));
				setLocation(newLocation);
			}
		}

	}

	// Tracks player based off the player's location; might want all player
	// locations to determine closest?
	public void act(Server server) {
		ArrayList<Player> players = server.getPlayerList();
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

		Rectangle attackArea = null;
		switch (getLocation().getDirection()) {
			case Location.NORTH:
				attackArea = new Rectangle(getLocation().getX() - getWidth() / 2, getLocation().getY() - getHeight()
						/ 2 - getHeight(), getWidth(), getHeight());
				break;
			case Location.SOUTH:
				attackArea = new Rectangle(getLocation().getX() - getWidth() / 2, getLocation().getY() + getHeight()
						/ 2, getWidth(), getHeight());
				break;
			case Location.EAST:
				attackArea = new Rectangle(getLocation().getX() + getWidth() / 2, getLocation().getY() - getHeight()
						/ 2, getWidth(), getHeight());
				break;
			case Location.WEST:
				attackArea = new Rectangle(getLocation().getX() - getWidth() / 2 - getWidth(), getLocation().getY()
						- getHeight() / 2, getWidth(), getHeight());
				break;
		}

		

		for(Player p : players){
			if(p instanceof Tank){
				track = p;
				break;
			}
			else
				track = players.get(smallestIndex);
		}
		
		if (server.getCurrentTick() % 10 == 0
				&& attackArea.intersects(new Rectangle(track.getLocation().getX() - track.getWidth() / 2, track
						.getLocation().getY() - track.getHeight() / 2, track.getWidth(), track.getHeight()))) {
			System.out.println(attackArea);
			System.out.println(new Rectangle(track.getLocation().getX() - track.getWidth() / 2, track.getLocation()
					.getY() - track.getHeight() / 2, track.getWidth(), track.getHeight()));
			attack(track);
		}
			if (this.getLocation().getDistanceTo(track.getLocation()) < 700) {
				if ((track.getLocation().getX() - this.getLocation().getX() != 0)
						&& Math.abs(track.getLocation().getX() - this.getLocation().getX()) < Math.abs(track
								.getLocation().getY() - this.getLocation().getY())) {
					move(Math.min(Math.abs(track.getLocation().getX() - getLocation().getX()), getVelocity()), track
							.getLocation().getX() - getLocation().getX(), "X");
				} else {
					move(Math.min(Math.abs(track.getLocation().getY() - getLocation().getY()), getVelocity()), track
							.getLocation().getY() - getLocation().getY(), "Y");
				}
			}
	

	}

	public Location getLocation() {
		return super.getLocation();
	}

	@Override
	public int getHeight() {
		return 80;
	}

	@Override
	public int getWidth() {
		return 112;
	}

	@Override
	public String getFileLocation() {
		return "Golem.png";
	}

	@Override
	public int getStrength() {
		return 100;
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
		return EntityType.GOLEM_ENEMY;
	}

	@Override
	public int getVelocity() {
		return 4;
	}

	public int getGoldValue() {
		return goldValue;
	}

	@Override
	public int getMaxHP() {
		return 500;
	}

}
