package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.NewProjectilePacket;

public class Archer extends Player {

	private long lastAttackFrame = 0;
	private boolean abilityActive = false;
	private long lastAbilityCall = -1;

	public Archer(int id, Location loc) {
		super(id, loc);
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
	}

	@Override
	public int getBaseDamage() {
		if (abilityActive) {
			return 50;
		} else {
			return 35;
		}

	}

	@Override
	public int getDamageVariance() {
		if (abilityActive)
			return 0;
		else
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
		return "Archer.png";
	}

	@Override
	public int getStrength() {
		return 20 + getLevel() * 2;
	}

	@Override
	public int getSpeed() {
		return 10 + getLevel() * 4;
	}

	@Override
	public int getIntelligence() {
		return 5 + getLevel();
	}

	@Override
	public int getVelocity() {
		return 6;
	}

	@Override
	public int getMaxHP() {
		return 100;
	}

	@Override
	public EntityType getType() {
		return EntityType.ARCHER;
	}

	@Override
	public void attack(Client client) {
		if (client.getFrame() - lastAttackFrame > 15) { // 2 arrows/sec
			Arrow projectile = new Arrow(-1, getLocation(), getDamageNumber());
			int packetId = Client.getNextPacketId();
			client.getUnacknowledgedProjectiles().put(packetId, projectile);
			client.getNetworkThread().sendPacket(new NewProjectilePacket(packetId, EntityType.ARROW, getLocation()));
			lastAttackFrame = client.getFrame();
		}
	}

	@Override
	public void useAbility(long frame, Client client) {
		if (lastAbilityCall == -1 || frame - lastAbilityCall > 600) { // 600 frames = 20 seconds after last ability call (10 seconds after ability ended)
			abilityActive = true;
			lastAbilityCall = frame;
		}
	}

	@Override
	public void updateAbility(long frame) {
		if (frame - lastAbilityCall > 300 && abilityActive) { // it has been more than 300 frames (=10 seconds)
			abilityActive = false;
		}
	}

	public boolean isAbilityActive() {
		return this.abilityActive;
	}

}
