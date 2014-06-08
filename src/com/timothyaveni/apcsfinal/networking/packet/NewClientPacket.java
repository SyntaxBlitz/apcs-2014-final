package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewClientPacket extends Packet {

	private EntityType type;

	public NewClientPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public NewClientPacket(int id, EntityType type) {
		super(id);
		this.setMustAcknowledge(false);
		this.type = type;
	}

	public EntityType getEntityType() {
		return this.type;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, EntityTypeID.getID(type), 6, 1);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.type = EntityTypeID.getType(ByteArrayTools.readBytes(data, 6, 1, false));
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[7];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_CLIENT;
	}

}
