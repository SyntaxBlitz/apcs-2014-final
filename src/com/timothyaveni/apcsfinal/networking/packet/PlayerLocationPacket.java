package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public class PlayerLocationPacket extends Packet {

	public PlayerLocationPacket(int id) {
		super(id);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PLAYER_LOCATION;
	}

}
