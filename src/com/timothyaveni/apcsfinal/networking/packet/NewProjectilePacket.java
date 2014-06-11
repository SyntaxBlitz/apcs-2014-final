package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewProjectilePacket extends Packet {

	private EntityType type;
	private Location location;

	public NewProjectilePacket(int id, EntityType type, Location location) {
		super(id);
		this.setMustAcknowledge(true);
		this.type = type;
		this.location = location;
	}

	public NewProjectilePacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public EntityType getType() {
		return type;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, EntityTypeID.getID(type), 6, 1);
		ByteArrayTools.setBytes(data, location.getWorldSectionId(), 7, 1);
		ByteArrayTools.setBytes(data, location.getX(), 8, 4);
		ByteArrayTools.setBytes(data, location.getY(), 12, 4);
		ByteArrayTools.setBytes(data, location.getDirection(), 16, 1);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.type = EntityTypeID.getType(ByteArrayTools.readBytes(data, 6, 1, false));
		this.location = new Location(ByteArrayTools.readBytes(data, 8, 4, true), ByteArrayTools.readBytes(data, 12, 4,
				true), ByteArrayTools.readBytes(data, 16, 1, false), ByteArrayTools.readBytes(data, 7, 1, false));
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[17];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_PROJECTILE;
	}

}
