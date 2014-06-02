package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;
import com.timothyaveni.apcsfinal.networking.PacketTypeID;

public abstract class Packet {

	private int id;
	private int remoteId;
	private int senderId;
	private boolean mustAcknowledge;

	public Packet(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setRemoteId(int id) {
		this.remoteId = id;
	}

	public int getRemoteId() {
		return this.remoteId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public boolean isMustAcknowledge() {
		return mustAcknowledge;
	}

	public void setMustAcknowledge(boolean mustAcknowledge) {
		this.mustAcknowledge = mustAcknowledge;
	}

	protected void unpack(byte[] data) {
		this.remoteId = ByteArrayTools.readBytes(data, 1, 3, false);
		this.senderId = ByteArrayTools.readBytes(data, 4, 1, false);
		this.mustAcknowledge = (ByteArrayTools.readBytes(data, 5, 1, false) == 1);
	}

	protected void pack(byte[] data) {
		ByteArrayTools.setBytes(data, PacketTypeID.getID(this.getPacketType()), 0, 1);
		ByteArrayTools.setBytes(data, this.id, 1, 3);
		ByteArrayTools.setBytes(data, this.senderId, 4, 1);
		ByteArrayTools.setBytes(data, this.mustAcknowledge ? 1 : 0, 5, 1);
	}

	public abstract byte[] getByteArray();

	public abstract PacketType getPacketType();

}
