package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewProjectileAcknowledgePacket extends Packet {

	private int acknowledgePacketId;
	private int newProjectileId;

	public NewProjectileAcknowledgePacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public NewProjectileAcknowledgePacket(int id, int acknowledgePacketId, int newProjectileId) {
		super(id);
		this.setMustAcknowledge(true);
		this.acknowledgePacketId = acknowledgePacketId;
		this.newProjectileId = newProjectileId;
	}

	public int getAcknowledgePacketId() {
		return acknowledgePacketId;
	}

	public int getNewProjectileId() {
		return newProjectileId;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, acknowledgePacketId, 6, 3);
		ByteArrayTools.setBytes(data, newProjectileId, 9, 2);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		acknowledgePacketId = ByteArrayTools.readBytes(data, 6, 3, false);
		newProjectileId = ByteArrayTools.readBytes(data, 9, 2, false);
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[11];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_PROJECTILE_ACKNOWLEDGE;
	}

}
