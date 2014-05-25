package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public class AcknowledgePacket extends Packet {

	public AcknowledgePacket(int id) {
		super(id);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ACKNOWLEDGE;
	}

}
