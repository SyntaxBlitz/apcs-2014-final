package com.timothyaveni.apcsfinal.networking;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.packet.PlayerLocationPacket;

public class PacketProcessor {

	private byte[] data;
	private int localPacketId;

	public PacketProcessor(byte[] data, int localPacketId) {
		this.data = data;
		this.localPacketId = localPacketId;
	}

	public Packet getPacket() {
		Packet toReturn;
		PacketType type = PacketTypeID.getType((int) data[0]);

		switch (type) {
			case ACKNOWLEDGE:
				toReturn = new AcknowledgePacket(localPacketId, data);
			case PLAYER_LOCATION:
				toReturn = new PlayerLocationPacket(localPacketId, data);
			case ENTITY_DAMAGE:
				toReturn = new EntityDamagePacket(localPacketId, data);
		}
		return null;
	}

}
