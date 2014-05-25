package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public class EnemyDamagePacket extends Packet {

	public EnemyDamagePacket(int id) {
		super(id);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENEMY_DAMAGE;
	}

}
