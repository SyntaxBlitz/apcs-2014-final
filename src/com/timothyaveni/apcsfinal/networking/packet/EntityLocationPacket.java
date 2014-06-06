package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class EntityLocationPacket extends Packet {

	private int entityId;
	private int worldSectionId;
	private Location location;

	public EntityLocationPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public EntityLocationPacket(int id, int entityId, int worldSectionId, Location location) {
		super(id);
		this.setMustAcknowledge(false);
		this.entityId = entityId;
		this.worldSectionId = worldSectionId;
		this.location = location;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getWorldSectionId() {
		return this.worldSectionId;
	}

	public Location getLocation() {
		return this.location;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, entityId, 6, 2);
		ByteArrayTools.setBytes(data, worldSectionId, 8, 1);
		ByteArrayTools.setBytes(data, location.getX(), 9, 4);
		ByteArrayTools.setBytes(data, location.getY(), 13, 4);
		ByteArrayTools.setBytes(data, location.getDirection(), 17, 1);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.entityId = ByteArrayTools.readBytes(data, 6, 2, false);
		this.worldSectionId = ByteArrayTools.readBytes(data, 8, 1, false);
		this.location = new Location(ByteArrayTools.readBytes(data, 9, 4, true), ByteArrayTools.readBytes(data, 13, 4,
				true), ByteArrayTools.readBytes(data, 17, 1, false));
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[17];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_LOCATION;
	}

}
