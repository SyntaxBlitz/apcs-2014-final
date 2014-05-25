package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public class AcknowledgePacket extends Packet {
	
	private int acknowledgedPacketId;

	public AcknowledgePacket(int id, byte[] data) {
		super(id);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ACKNOWLEDGE;
	}

}
