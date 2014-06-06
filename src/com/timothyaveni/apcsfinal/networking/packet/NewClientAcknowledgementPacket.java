package com.timothyaveni.apcsfinal.networking.packet;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;
import com.timothyaveni.apcsfinal.networking.PacketType;

public class NewClientAcknowledgementPacket extends Packet {

	private int newClientId;
	private int playerEntityId;

	public NewClientAcknowledgementPacket(int id, byte[] data) {
		super(id);
		unpack(data);
	}

	public NewClientAcknowledgementPacket(int id, int newClientId, int playerEntityId) {
		super(id);
		this.setMustAcknowledge(true);
		this.newClientId = newClientId;
		this.playerEntityId = playerEntityId;
	}
	
	public int getNewClientId() {
		return this.newClientId;
	}
	
	public int getPlayerEntityId() {
		return this.playerEntityId;
	}

	@Override
	public void pack(byte[] data) {
		super.pack(data);
		ByteArrayTools.setBytes(data, this.newClientId, 6, 1);
		ByteArrayTools.setBytes(data, this.playerEntityId, 7, 2);
		System.out.println(Integer.toBinaryString(this.playerEntityId));
		System.out.println(Integer.toBinaryString(ByteArrayTools.readBytes(data, 7, 2, false)));
	}

	@Override
	public void unpack(byte[] data) {
		super.unpack(data);
		System.out.println(ByteArrayTools.readBytes(data, 7, 2, false));
		this.newClientId = ByteArrayTools.readBytes(data, 6, 1, false);
		this.playerEntityId = ByteArrayTools.readBytes(data, 7, 2, false);
	}

	@Override
	public byte[] getByteArray() {
		byte[] toReturn = new byte[9];
		pack(toReturn);
		return toReturn;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_CLIENT_ACKNOWLDEGEMENT;
	}

}
