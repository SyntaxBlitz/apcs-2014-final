package com.timothyaveni.apcsfinal.client;

import java.awt.Rectangle;
import java.util.Iterator;

import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;

public class Tank extends Player {

	private boolean abilityActive = false;
	private long lastAbilityCall;

	public Tank(int id, Location loc) {
		super(id, loc);
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
		return "Tank.png";
	}

	@Override
	public int getStrength() {
		return 10 + getLevel() * 5;
	}

	@Override
	public int getSpeed() {
		return 5 + getLevel() * 2;
	}

	@Override
	public int getIntelligence() {
		return 3 + getLevel();
	}

	@Override
	public int getBaseDamage() {
		return 12;
	}

	@Override
	public EntityType getType() {
		return EntityType.TANK;
	}

	@Override
	public int getMaxHP() {
		if (abilityActive)
			return 350;
		else
			return 250;
	}

	@Override
	public int getVelocity() {
		return 4;
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
				System.out.println(entity.getId());
				client.getNetworkThread().sendPacket(
						new EntityDamagePacket(Client.getNextPacketId(), entity.getId(), getBaseDamage()));
			}
		}
	}

	@Override
	public void useAbility(long frame) {
		abilityActive = true;
		lastAbilityCall = frame;
		setHP(getHP() + 100);
	}

	@Override
	public void updateAbility(long frame) {
		if (frame - lastAbilityCall > 300) {	// it has been more than 300 frames (=10 seconds)
			abilityActive = false;
			if (getHP() >= 100) // checks to make sure resetting HP will not kill player
				setHP(getHP() - 100);
			else
				setHP(1);
		}
	}

}
