package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.AnimationType;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EnvironmentAnimationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewProjectilePacket;

public class Mage extends Player {

	private long lastAbilityCall = -1;

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
	public int getDamageVariance() {
		return 0;
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
		if (client.getFrame() - lastAttackFrame > 30) { // 1 magic ball every second
			MagicBall projectile = new MagicBall(-1, getLocation(), getDamageNumber());
			int packetId = Client.getNextPacketId();
			client.getUnacknowledgedProjectiles().put(packetId, projectile);
			client.getNetworkThread().sendPacket(
					new NewProjectilePacket(packetId, EntityType.MAGIC_BALL, getLocation()));
			lastAttackFrame = client.getFrame();
		}
	}

	@Override
	public void useAbility(long frame, Client client) {
		if (lastAbilityCall == -1 || frame - lastAbilityCall > 150) {
			Entity[] entities = client.getEntityList().values().toArray(new Entity[0]);
			for (Entity entity : entities) {
				if (!(entity instanceof Player)
						&& getLocation().getDistanceTo(entity.getLocation()) <= getAttackRadius() * 3) {
					client.getNetworkThread().sendPacket(
							new EntityDamagePacket(Client.getNextPacketId(), entity.getId(), getBaseDamage() + 10));
					client.getNetworkThread().sendPacket(
							new EnvironmentAnimationPacket(Client.getNextPacketId(), AnimationType.DAMAGE_NUMBER,
									entity.getLocation(), getBaseDamage() + 10));
				}
			}
			client.getNetworkThread().sendPacket(
					new EnvironmentAnimationPacket(Client.getNextPacketId(), AnimationType.LIGHTNING_BOLT,
							getLocation(), 00));
			lastAbilityCall = frame;
		}
	}

	@Override
	public void updateAbility(long frame) {

	}
}
