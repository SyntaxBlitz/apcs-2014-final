package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public class EntityDamagePacket extends Packet {

	private int entityId;
	private int damageAmount;

	public EntityDamagePacket(int id, byte[] data) {
		super(id);
	}

	public EntityDamagePacket(int id, int entityId, int damageAmount) {
		super(id);
		this.entityId = entityId;
		this.damageAmount = damageAmount;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getDamageAmount() {
		return this.damageAmount;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_DAMAGE;
	}

}
