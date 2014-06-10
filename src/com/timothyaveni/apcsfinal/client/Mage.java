package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.NewProjectilePacket;

public class Mage extends Player {

	long lastAttackFrame = 0;
	
	public Mage(int id, Location loc) {
		super(id, loc);
	}

	@Override
	public double getAttackRadius() {
		return this.getHeight();
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
		return "Mage.png";
	}

	@Override
	public int getStrength() {
		return 5 + getLevel();
	}

	@Override
	public int getSpeed() {
		return 5 + getLevel();
	}

	@Override
	public int getIntelligence() {
		return 10 + getLevel() * 5;
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
		return EntityType.MAGE;
	}

	@Override
	public void attack(Client client) {
		if (client.getFrame() - lastAttackFrame > 60) {	// 1 magic ball every 2 seconds
			MagicBall projectile = new MagicBall(-1, getLocation());
			int packetId = Client.getNextPacketId();
			client.getUnacknowledgedProjectiles().put(packetId, projectile);
			client.getNetworkThread().sendPacket(new NewProjectilePacket(packetId, EntityType.MAGIC_BALL, getLocation()));
			lastAttackFrame = client.getFrame();
		}
	}

	@Override
	public void useAbility(long frame, Client client) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAbility(long frame) {
		// TODO Auto-generated method stub

	}
}
