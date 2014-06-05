package com.timothyaveni.apcsfinal.networking;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
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
		Packet toReturn = null;
		PacketType type = PacketTypeID.getType((int) data[0]);

		switch (type) {
			case ACKNOWLEDGE:
				toReturn = new AcknowledgePacket(localPacketId, data);
			case PLAYER_LOCATION:
				toReturn = new PlayerLocationPacket(localPacketId, data);
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
