package com.timothyaveni.apcsfinal.client;

import java.awt.Rectangle;
import java.util.Iterator;

import com.timothyaveni.apcsfinal.networking.AnimationType;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EnvironmentAnimationPacket;

public class Healer extends Player {

	private long lastAbilityCall;

	public Healer(int id, Location loc) {
		super(id, loc);
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
	}

	@Override
	public int getBaseDamage() {
		return 10;
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
		return "Healer.png";
	}

	@Override
	public int getStrength() {
		return 15 + getLevel() * 2;
	}

	@Override
	public int getSpeed() {
		return 5 + getLevel();
	}

	@Override
	public int getIntelligence() {
		return 15 + getLevel() * 3;
	}

	@Override
	public int getVelocity() {
		return 6;
	}

	@Override
	public int getMaxHP() {
		return 75;
	}

	@Override
	public EntityType getType() {
		return EntityType.HEALER;
	}

	@Override
	public void attack(Client client) {
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

		Entity[] entities = client.getEntityList().values().toArray(new Entity[0]);

		for (Entity entity: entities) {
			if (entity instanceof Player)
				continue;
			Location entityLoc = entity.getLocation();
			if (attackArea.intersects(entityLoc.getX() - entity.getWidth() / 2, entityLoc.getY() - entity.getHeight()
					/ 2, entity.getWidth(), entity.getHeight())) {
				client.getNetworkThread().sendPacket(
						new EntityDamagePacket(Client.getNextPacketId(), entity.getId(), getBaseDamage()));
				client.getNetworkThread().sendPacket(
						new EnvironmentAnimationPacket(Client.getNextPacketId(), AnimationType.DAMAGE_NUMBER, entity
								.getLocation(), getBaseDamage()));
			}
		}
	}

	@Override
	public void useAbility(long frame, Client client) {
		if (frame - lastAbilityCall > 300) {
			Entity[] entities = client.getEntityList().values().toArray(new Entity[0]);
			for (Entity entity : entities) {
				if (entity instanceof Player
						&& getLocation().getDistanceTo(entity.getLocation()) <= getAttackRadius() * 3) {
					client.getNetworkThread().sendPacket(
							new EntityDamagePacket(Client.getNextPacketId(), entity.getId(), -30));
					client.getNetworkThread().sendPacket(
							new EnvironmentAnimationPacket(Client.getNextPacketId(), AnimationType.DAMAGE_NUMBER,
									entity.getLocation(), -30));
				}
			}
			lastAbilityCall = frame;
		}
	}

	@Override
	public void updateAbility(long frame) {

	}
}
