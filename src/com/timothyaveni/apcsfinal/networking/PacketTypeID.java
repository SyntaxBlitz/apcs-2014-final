package com.timothyaveni.apcsfinal.networking;

public class PacketTypeID {

	public static int getID(PacketType type) {
		switch (type) {
		case ACKNOWLEDGE:
			return 0;
		case PLAYER_LOCATION:
			return 1;
		case ENTITY_DAMAGE:
			return 2;
		case NEW_ENTITY:
			return 3;
		case NEW_CLIENT:
			return 4;
		case NEW_CLIENT_ACKNOWLDEGEMENT:
			return 5;
		default:
			return -1;
		}
	}

	public static PacketType getType(int id) {
		switch (id) {
		case 0:
			return PacketType.ACKNOWLEDGE;
		case 1:
			return PacketType.PLAYER_LOCATION;
		case 2:
			return PacketType.ENTITY_DAMAGE;
		case 3:
			return PacketType.NEW_ENTITY;
		case 4:
			return PacketType.NEW_CLIENT;
		case 5:
			return PacketType.NEW_CLIENT_ACKNOWLDEGEMENT;
		default:
			return null;
		}
	}

}
