package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewEntityPacket extends Packet {

	private Entity entity;

	public NewEntityPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}
	
	public NewEntityPacket(int id, Entity entity) {
		super(id);
		this.setMustAcknowledge(true);
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return this.entity;
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
		byte[] toReturn = new byte[1];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_ENTITY;
	}

}
