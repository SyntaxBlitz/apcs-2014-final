package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class EntityDamagePacket extends Packet {

	private int entityId;
	private int damageAmount;

	public EntityDamagePacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public EntityDamagePacket(int id, int entityId, int damageAmount) {
		super(id);
		this.entityId = entityId;
		this.damageAmount = damageAmount;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getDamageAmount() {
		return this.damageAmount;
	}

	@Override
	protected void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, entityId, 6, 2);
		ByteArrayTools.setBytes(data, damageAmount, 8, 3);
	}
	
	@Override
	protected void unpack(byte[] data) {
		super.unpack(data);
		this.entityId = ByteArrayTools.readBytes(data, 6, 2, false);
		this.damageAmount = ByteArrayTools.readBytes(data, 6, 3, true);
	}
	
	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[10];
		pack(toReturn);
		return toReturn;
	}
	
	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_DAMAGE;
	}

}
