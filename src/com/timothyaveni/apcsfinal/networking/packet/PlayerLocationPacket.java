package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class PlayerLocationPacket extends Packet {

	private int worldSectionId;
	private Location location;

	public PlayerLocationPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public PlayerLocationPacket(int id, int worldSectionId, Location location) {
		super(id);
		this.setMustAcknowledge(false);
		this.worldSectionId = worldSectionId;
		this.location = location;
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
		ByteArrayTools.setBytes(data, worldSectionId, 6, 1);
		ByteArrayTools.setBytes(data, location.getX(), 7, 4);
		ByteArrayTools.setBytes(data, location.getY(), 11, 4);
		ByteArrayTools.setBytes(data, location.getDirection(), 15, 1);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.worldSectionId = ByteArrayTools.readBytes(data, 6, 1, false);
		this.location = new Location(ByteArrayTools.readBytes(data, 7, 4, true), ByteArrayTools.readBytes(data, 11, 4,
				true), ByteArrayTools.readBytes(data, 15, 1, false));
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[15];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PLAYER_LOCATION;
	}

}
