package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class AcknowledgePacket extends Packet {
	
	private int acknowledgedPacketId;

	public AcknowledgePacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public AcknowledgePacket(int id, int remotePacketId) {
		super(id);
		this.acknowledgedPacketId = remotePacketId;
	}
	
	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, acknowledgedPacketId, 6, 3);
	}
	
	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.acknowledgedPacketId = ByteArrayTools.readBytes(data, 6, 3, false);
	}
	
	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[8];
		pack(toReturn);
		return toReturn;
	}
	
	@Override
	public PacketType getPacketType() {
		return PacketType.ACKNOWLEDGE;
	}

}
