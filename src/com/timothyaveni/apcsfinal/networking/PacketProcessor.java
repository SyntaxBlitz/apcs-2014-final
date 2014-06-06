package com.timothyaveni.apcsfinal.networking;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;

public class PacketProcessor {

	private byte[] data;
	private int localPacketId;

	public PacketProcessor(byte[] data, int localPacketId) {
		this.data = data;
		this.localPacketId = localPacketId;
	}

	public Packet getPacket() {
		Packet toReturn = null;
		int packetTypeID = ByteArrayTools.readBytes(data, 0, 1, false);
		PacketType type = PacketTypeID.getType(packetTypeID);

		switch (type) {
			case ACKNOWLEDGE:
				toReturn = new AcknowledgePacket(localPacketId, data);
			case ENTITY_LOCATION:
				toReturn = new EntityLocationPacket(localPacketId, data);
			case ENTITY_DAMAGE:
				toReturn = new EntityDamagePacket(localPacketId, data);
			case NEW_ENTITY:
				toReturn = new NewEntityPacket(localPacketId, data);
			case NEW_CLIENT:
				toReturn = new NewClientPacket(localPacketId, data);
			case NEW_CLIENT_ACKNOWLDEGEMENT:
				toReturn = new NewClientAcknowledgementPacket(localPacketId, data);
		}

		return toReturn;
	}

}
