package com.timothyaveni.apcsfinal.networking;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EnemyDamagePacket;
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
			case ENEMY_DAMAGE:
				toReturn = new EnemyDamagePacket(localPacketId, data);
		}
		return null;
	}

}
