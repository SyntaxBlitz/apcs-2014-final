package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewClientAcknowledgementPacket extends Packet {

	private int newClientId;

	public NewClientAcknowledgementPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public NewClientAcknowledgementPacket(int id, int newClientId) {
		super(id);
		this.setMustAcknowledge(true);
		this.newClientId = newClientId;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, this.newClientId, 6, 1);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.newClientId = ByteArrayTools.readBytes(data, 6, 1, false);
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[6];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_CLIENT_ACKNOWLDEGEMENT;
	}

}
