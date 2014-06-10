package com.timothyaveni.apcsfinal.client;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;

import com.timothyaveni.apcsfinal.networking.AnimationType;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EnvironmentAnimationPacket;

public class Rogue extends Player {

	public Rogue(int id, Location loc) {
		super(id, loc);
	}

	@Override
	public int getBaseDamage() {
		return 35;
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

		return "Rogue.png";
	}

	@Override
	public int getStrength() {
		return 10 + getLevel();
	}

	@Override
	public int getSpeed() {
		return 10 + getLevel();
	}

	@Override
	public int getIntelligence() {
		return 5 + getLevel();
	}

	@Override
	public int getVelocity() {
		return 8;
	}

	@Override
	public int getMaxHP() {
		return 50;
	}

	@Override
	public EntityType getType() {
		return EntityType.ROGUE;
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
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

		Iterator<Entity> i = client.getEntityList().values().iterator();
		while (i.hasNext()) {
			Entity entity = i.next();
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
	public void useAbility(long frame) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAbility(long frame) {
		// TODO Auto-generated method stub

	}

}
