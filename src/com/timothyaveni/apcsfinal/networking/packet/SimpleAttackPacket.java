package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class SimpleAttackPacket extends Packet {

	private int entityId;
	private boolean attacking;

	public SimpleAttackPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public SimpleAttackPacket(int id, int entityId, boolean attacking) {
		super(id);
		this.setMustAcknowledge(false); // nah not really
		this.entityId = entityId;
		this.attacking = attacking;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public boolean isAttacking() {
		return this.attacking;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, entityId, 6, 2);
		ByteArrayTools.setBytes(data, attacking ? 1 : 0, 8, 1);
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		this.entityId = ByteArrayTools.readBytes(data, 6, 2, false);
		this.attacking = ByteArrayTools.readBytes(data, 8, 1, false) == 1;
	}
	
	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[9];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.SIMPLE_ATTACK;
	}

}
