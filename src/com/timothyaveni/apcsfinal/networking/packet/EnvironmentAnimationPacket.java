package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.networking.AnimationType;
import com.timothyaveni.apcsfinal.networking.AnimationTypeID;
import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class EnvironmentAnimationPacket extends Packet {

	private AnimationType type;
	private Location location;
	private int data;

	public EnvironmentAnimationPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public EnvironmentAnimationPacket(int id, AnimationType type, Location location, int data) {
		super(id);
		this.setMustAcknowledge(true);
		this.type = type;
		this.location = location;
		this.data = data;
	}

	public AnimationType getAnimationType() {
		return this.type;
	}

	public Location getLocation() {
		return this.location;
	}

	public int getData() {
		return this.data;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, AnimationTypeID.getID(type), 6, 1);
		ByteArrayTools.setBytes(data, location.getWorldSectionId(), 7, 1);
		ByteArrayTools.setBytes(data, location.getX(), 8, 4);
		ByteArrayTools.setBytes(data, location.getY(), 12, 4);
		ByteArrayTools.setBytes(data, location.getDirection(), 16, 4);
		ByteArrayTools.setBytes(data, this.data, 17, 3);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.type = AnimationTypeID.getType(ByteArrayTools.readBytes(data, 6, 1, false));
		this.location = new Location(ByteArrayTools.readBytes(data, 8, 4, true), ByteArrayTools.readBytes(data, 12, 4,
				true), ByteArrayTools.readBytes(data, 16, 1, false), ByteArrayTools.readBytes(data, 7, 1, false));
		this.data = ByteArrayTools.readBytes(data, 17, 3, true);
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[20];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENVIRONMENT_ANIMATION_PACKET;
	}

}
