package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewEntityPacket extends Packet {

	private EntityType entityType;
	private int entityId;
	private Location entityLocation;

	public NewEntityPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public NewEntityPacket(int id, Entity entity) {
		super(id);
		this.setMustAcknowledge(true);
		this.entityType = entity.getType();
		this.entityId = entity.getId();
		this.entityLocation = entity.getLocation();
	}

	public EntityType getEntityType() {
		return this.entityType;
	}
	
	public int getEntityId() {
		return this.entityId;
	}
	
	public Location getEntityLocation() {
		return this.entityLocation;
	}
	
	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, EntityTypeID.getID(entityType), 6, 1);
		ByteArrayTools.setBytes(data, entityId, 7, 2);
		ByteArrayTools.setBytes(data, entityLocation.getX(), 9, 4);
		ByteArrayTools.setBytes(data, entityLocation.getY(), 13, 4);
		ByteArrayTools.setBytes(data, entityLocation.getDirection(), 17, 1);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.entityType = EntityTypeID.getType(ByteArrayTools.readBytes(data, 6, 1, false));
		this.entityId = ByteArrayTools.readBytes(data, 7, 2, false);
		this.entityLocation = new Location(ByteArrayTools.readBytes(data, 9, 4, true), ByteArrayTools.readBytes(data,
				13, 4, true), ByteArrayTools.readBytes(data, 17, 1, false));
		System.out.println(entityLocation);
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[18];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_ENTITY;
	}

}
