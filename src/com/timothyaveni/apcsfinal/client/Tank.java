package com.timothyaveni.apcsfinal.client;

import java.awt.Rectangle;
import java.util.Iterator;

import com.timothyaveni.apcsfinal.networking.AnimationType;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EnvironmentAnimationPacket;

public class Tank extends Player {

	private boolean abilityActive = false;
	private long lastAbilityCall = -1;

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
		return 10;
	}
	
	@Override
	public int getDamageVariance() {
		return 3;
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

		Entity[] entities = client.getEntityList().values().toArray(new Entity[0]);

		for (Entity entity: entities) {
			if (entity instanceof Player)
				continue;
			Location entityLoc = entity.getLocation();
			if (attackArea.intersects(entityLoc.getX() - entity.getWidth() / 2, entityLoc.getY() - entity.getHeight()
					/ 2, entity.getWidth(), entity.getHeight())) {
				int damageAmount = getDamageNumber();
				client.getNetworkThread().sendPacket(
						new EntityDamagePacket(Client.getNextPacketId(), entity.getId(), damageAmount));
				client.getNetworkThread().sendPacket(
						new EnvironmentAnimationPacket(Client.getNextPacketId(), AnimationType.DAMAGE_NUMBER, entity
								.getLocation(), damageAmount));
			}
		}
	}

	@Override
	public void useAbility(long frame, Client client) {
		if (lastAbilityCall == -1 || frame - lastAbilityCall > 600) { // 600 frames = 20 seconds after last ability call (10 seconds after ability ended)
			abilityActive = true;
			client.getNetworkThread().sendPacket(
					new EnvironmentAnimationPacket(Client.getNextPacketId(), AnimationType.RAGE,
							getLocation(), 00));
			lastAbilityCall = frame;
			setHP(getHP() + 100);
		}
	}

	@Override
	public void updateAbility(long frame) {
		if (frame - lastAbilityCall > 300 && abilityActive) { // it has been more than 300 frames (=10 seconds)
			abilityActive = false;
			if (getHP() >= 100) // checks to make sure resetting HP will not kill player
				setHP(getHP() - 100);
			else
				setHP(1);
		}
	}

	public boolean isAbilityActive() {
		return this.abilityActive;
	}

}
