package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.PacketType;

public abstract class Packet {

	private int id;
	private int remoteId;

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

	public abstract PacketType getPacketType();

}
