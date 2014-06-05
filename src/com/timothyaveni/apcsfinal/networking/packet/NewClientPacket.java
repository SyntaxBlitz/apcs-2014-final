package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewClientPacket extends Packet {

	public NewClientPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}
	
	public NewClientPacket(int id) {
		super(id);
		this.setMustAcknowledge(false);
	}
	
	@Override
	public void pack(byte[] data) {
		super.pack(data);
	}
	
	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[5];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_CLIENT;
	}

}
