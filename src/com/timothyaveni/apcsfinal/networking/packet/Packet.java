package com.timothyaveni.apcsfinal.networking.packet;

public abstract class Packet {

	private int id;
	
	public Packet(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
