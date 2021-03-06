package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.client.ArcanusBall;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;

public class ArcanusEnemy extends Entity implements BossAI {
	private int baseDmg = 30;
	private int goldValue = 40;

	private boolean attacked = false;

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

	// This method determines if the boss fires projectiles or if the boss spawns enemies as his act every 5 seconds
	public void act(Server server) {
		Player[] players = server.getPlayerList().toArray(new Player[0]);
		boolean anyoneHere = false;
		for (Player player : players) {
			if (player.getLocation().getWorldSectionId() == getLocation().getWorldSectionId()) {
				anyoneHere = true;
				break;
			}
		}

		if (anyoneHere) {
			if (getHP() < 1000) {
				if (server.getCurrentTick() % 200 == 0)
					summonMinions(server);
			} else {
				if (server.getCurrentTick() % 50 == 0)
					projectileAttack(server);
			}
		}

		if (server.getCurrentTick() % 50 == 5 && attacked) {
			Server.addPacketToQueue(new SimpleAttackPacket(Server.getNextPacketId(), getId(), false));
			attacked = false;
		}
	}

	// This method adds enemy entities to the server list of invisible entities
	public void summonMinions(Server server) {

		GolemEnemy golem1 = new GolemEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));
		GolemEnemy golem2 = new GolemEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));
		SkeletonEnemy skelly1 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));
		SkeletonEnemy skelly2 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));
		SkeletonEnemy skelly3 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));
		SkeletonEnemy skelly4 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));
		SkeletonEnemy skelly5 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));
		SkeletonEnemy skelly6 = new SkeletonEnemy(Server.getNextEntityId(), new Location(0, 0, 0, 0));

		golem1.setLocation(new Location(this.getLocation().getX() + (this.getWidth() / 2 + golem1.getWidth()), this
				.getLocation().getY(), Location.EAST, this.getLocation().getWorldSectionId()));

		golem2.setLocation(new Location(this.getLocation().getX() - (this.getWidth() / 2 + golem1.getWidth()), this
				.getLocation().getY(), Location.WEST, this.getLocation().getWorldSectionId()));

		skelly1.setLocation(new Location(this.getLocation().getX() - (this.getWidth() / 2 + skelly1.getWidth() / 2),
				this.getLocation().getY() - (this.getHeight() / 2 + skelly1.getHeight() / 2), Location.WEST, this
						.getLocation().getWorldSectionId()));

		skelly2.setLocation(new Location(this.getLocation().getX(), this.getLocation().getY()
				- (this.getHeight() / 2 + skelly2.getHeight() / 2), Location.SOUTH, this.getLocation()
				.getWorldSectionId()));

		skelly3.setLocation(new Location(this.getLocation().getX() + (this.getWidth() / 2 + skelly3.getWidth() / 2),
				this.getLocation().getY() - (this.getHeight() / 2 + skelly3.getHeight() / 2), Location.EAST, this
						.getLocation().getWorldSectionId()));

		skelly4.setLocation(new Location(this.getLocation().getX() - (this.getWidth() / 2 + skelly4.getWidth() / 2),
				this.getLocation().getY() + (this.getHeight() / 2 + skelly4.getHeight() / 2), Location.WEST, this
						.getLocation().getWorldSectionId()));

		skelly5.setLocation(new Location(this.getLocation().getX(), this.getLocation().getY()
				+ (this.getHeight() / 2 + skelly5.getHeight() / 2), Location.SOUTH, this.getLocation()
				.getWorldSectionId()));

		skelly6.setLocation(new Location(this.getLocation().getX() + (this.getWidth() / 2 + skelly6.getWidth() / 2),
				this.getLocation().getY() + (this.getHeight() / 2 + skelly6.getHeight() / 2), Location.EAST, this
						.getLocation().getWorldSectionId()));

		server.getInvisibleEntityList().add(golem1);
		server.getInvisibleEntityList().add(golem2);
		server.getInvisibleEntityList().add(skelly1);
		server.getInvisibleEntityList().add(skelly2);
		server.getInvisibleEntityList().add(skelly3);
		server.getInvisibleEntityList().add(skelly4);
		server.getInvisibleEntityList().add(skelly5);
		server.getInvisibleEntityList().add(skelly6);

		server.getEntityList().put(golem1.getId(), golem1);
		server.getEntityList().put(golem2.getId(), golem2);
		server.getEntityList().put(skelly1.getId(), skelly1);
		server.getEntityList().put(skelly2.getId(), skelly2);
		server.getEntityList().put(skelly3.getId(), skelly3);
		server.getEntityList().put(skelly4.getId(), skelly4);
		server.getEntityList().put(skelly5.getId(), skelly5);
		server.getEntityList().put(skelly6.getId(), skelly6);

	}

	// This method creates new projectiles to send to the server
	public void projectileAttack(Server server) {
		for (int i = 0; i < 3; i++) {
			ArcanusBall ball1 = new ArcanusBall(Server.getNextEntityId(), new Location(this.getLocation().getX(), this
					.getLocation().getY(), Location.NORTH, this.getLocation().getWorldSectionId()));
			ArcanusBall ball2 = new ArcanusBall(Server.getNextEntityId(), new Location(this.getLocation().getX(), this
					.getLocation().getY(), Location.EAST, this.getLocation().getWorldSectionId()));
			ArcanusBall ball3 = new ArcanusBall(Server.getNextEntityId(), new Location(this.getLocation().getX(), this
					.getLocation().getY(), Location.SOUTH, this.getLocation().getWorldSectionId()));
			ArcanusBall ball4 = new ArcanusBall(Server.getNextEntityId(), new Location(this.getLocation().getX(), this
					.getLocation().getY(), Location.WEST, this.getLocation().getWorldSectionId()));
			server.getMyProjectiles().add(ball1);
			server.getMyProjectiles().add(ball2);
			server.getMyProjectiles().add(ball3);
			server.getMyProjectiles().add(ball4);
			Server.addPacketToQueue(new NewEntityPacket(Server.getNextPacketId(), ball1));
			Server.addPacketToQueue(new NewEntityPacket(Server.getNextPacketId(), ball2));
			Server.addPacketToQueue(new NewEntityPacket(Server.getNextPacketId(), ball3));
			Server.addPacketToQueue(new NewEntityPacket(Server.getNextPacketId(), ball4));
			Server.addPacketToQueue(new SimpleAttackPacket(Server.getNextPacketId(), getId(), true));
			attacked = true;
		}
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
