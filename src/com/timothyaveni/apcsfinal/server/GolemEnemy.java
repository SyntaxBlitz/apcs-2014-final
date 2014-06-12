package com.timothyaveni.apcsfinal.server;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.MapMetadata;
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

	public void move(int distance, int direction, String plane, MapMetadata map, HashMap<Integer, Entity> entities,
			ArrayList<Player> players) {
		Location newLocation = null;
		if (plane.equals("X")) {
			if (direction > 0) {
				newLocation = new Location(this.getLocation().getX() + distance, this.getLocation().getY(),
						Location.EAST, this.getLocation().getWorldSectionId());
			} else {
				newLocation = new Location(this.getLocation().getX() - distance, this.getLocation().getY(),
						Location.WEST, this.getLocation().getWorldSectionId());
			}
		} else {
			if (direction > 0) {
				newLocation = new Location(this.getLocation().getX(), this.getLocation().getY() + distance,
						Location.SOUTH, this.getLocation().getWorldSectionId());
			} else {
				newLocation = new Location(this.getLocation().getX(), this.getLocation().getY() - distance,
						Location.NORTH, this.getLocation().getWorldSectionId());
			}
		}

		if (map.isPointValid(newLocation.getX(), newLocation.getY()) && !collidesWith(newLocation, entities)
				&& !collidesWith(newLocation, players)) {
			Server.addPacketToQueue(new EntityLocationPacket(Server.getNextPacketId(), this.getId(), newLocation));
			setLocation(newLocation);
		}
	}

	private boolean collidesWith(Location newLocation, HashMap<Integer, Entity> entities) {
		Iterator<Entity> i = entities.values().iterator();
		while (i.hasNext()) {
			Entity entity = i.next();
			if (entity == this)
				continue;
			Location myLocation = newLocation;
			Location entityLocation = entity.getLocation();
			if (entityLocation.getWorldSectionId() == myLocation.getWorldSectionId()) {
				Rectangle entityRect = new Rectangle(entityLocation.getX() - entity.getWidth() / 2,
						entityLocation.getY() - entity.getHeight() / 2, entity.getWidth(), entity.getHeight());
				if (entityRect.intersects(new Rectangle(myLocation.getX() - getWidth() / 2, myLocation.getY()
						- getHeight() / 2, getWidth(), getHeight())))
					return true;
			}
		}
		return false;
	}

	private boolean collidesWith(Location newLocation, ArrayList<Player> players) {
		Iterator<Player> i = players.iterator();
		while (i.hasNext()) {
			Player player = i.next();
			Location myLocation = newLocation;
			Location playerLocation = player.getLocation();
			if (playerLocation.getWorldSectionId() == myLocation.getWorldSectionId()) {
				Rectangle entityRect = new Rectangle(playerLocation.getX() - player.getWidth() / 2,
						playerLocation.getY() - player.getHeight() / 2, player.getWidth(), player.getHeight());
				if (entityRect.intersects(new Rectangle(myLocation.getX() - getWidth() / 2, myLocation.getY()
						- getHeight() / 2, getWidth(), getHeight())))
					return true;
			}
		}
		return false;
	}

	// Tracks player based off the player's location; might want all player
	// locations to determine closest?
	public void act(Server server) {
		Player[] players = server.getPlayerList().toArray(new Player[0]);
		if (players.length == 0)
			return;
		Player track;

		double smallestDistance = -1;
		int smallestIndex = 0;

		for (int i = 0; i < players.length; i++) {
			Player player = players[i];

			if (getLocation().getWorldSectionId() != player.getLocation().getWorldSectionId())
				continue;

			double thisDistance = getLocation().getDistanceTo(players[i].getLocation());

			if (thisDistance < smallestDistance || smallestDistance == -1) {
				smallestDistance = thisDistance;
				smallestIndex = i;
			}
		}

		track = players[smallestIndex];

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

		for (Player p : players) {
			if (p instanceof Tank) {
				track = p;
				break;
			} else
				track = players[smallestIndex];
		}

		if (server.getCurrentTick() % 10 == 0
				&& attackArea.intersects(new Rectangle(track.getLocation().getX() - track.getWidth() / 2, track
						.getLocation().getY() - track.getHeight() / 2, track.getWidth(), track.getHeight()))) {
			setDirection(getDirectionTowards(track));
			attack(track);
		}
		if (this.getLocation().getDistanceTo(track.getLocation()) < 700) {
			if ((track.getLocation().getY() - this.getLocation().getY() == 0)) {
				move(Math.min(Math.abs(track.getLocation().getX() - getLocation().getX()), getVelocity()), track
						.getLocation().getX() - getLocation().getX(), "X",
						server.getLoadedMaps().get(getLocation().getWorldSectionId()), server.getEntityList(),
						server.getPlayerList());
			} else {
				move(Math.min(Math.abs(track.getLocation().getY() - getLocation().getY()), getVelocity()), track
						.getLocation().getY() - getLocation().getY(), "Y",
						server.getLoadedMaps().get(getLocation().getWorldSectionId()), server.getEntityList(),
						server.getPlayerList());
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
