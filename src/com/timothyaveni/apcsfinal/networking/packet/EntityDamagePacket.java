package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public class EntityDamagePacket extends Packet {

	public EntityDamagePacket(int id, byte[] data) {
		super(id);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_DAMAGE;
	}

}
