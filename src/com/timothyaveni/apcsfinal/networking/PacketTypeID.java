package com.timothyaveni.apcsfinal.networking;

public class PacketTypeID {

	public static int getID(PacketType type) {
		switch (type) {
			case ACKNOWLEDGE:
				return 0;
			case ENTITY_LOCATION:
				return 1;
			case ENTITY_DAMAGE:
				return 2;
			case NEW_ENTITY:
				return 3;
			case NEW_CLIENT:
				return 4;
			case NEW_CLIENT_ACKNOWLDEGEMENT:
				return 5;
			case SIMPLE_ATTACK:
				return 6;
			case NEW_PROJECTILE:
				return 7;
			case NEW_PROJECTILE_ACKNOWLEDGE:
				return 8;
			case ENVIRONMENT_ANIMATION_PACKET:
				return 9;
			default:
				return -1;
		}
	}

	public static PacketType getType(int id) {
		switch (id) {
			case 0:
				return PacketType.ACKNOWLEDGE;
			case 1:
				return PacketType.ENTITY_LOCATION;
			case 2:
				return PacketType.ENTITY_DAMAGE;
			case 3:
				return PacketType.NEW_ENTITY;
			case 4:
				return PacketType.NEW_CLIENT;
			case 5:
				return PacketType.NEW_CLIENT_ACKNOWLDEGEMENT;
			case 6:
				return PacketType.SIMPLE_ATTACK;
			case 7:
				return PacketType.NEW_PROJECTILE;
			case 8:
				return PacketType.NEW_PROJECTILE_ACKNOWLEDGE;
			case 9:
				return PacketType.ENVIRONMENT_ANIMATION_PACKET;
			default:
				return null;
		}
	}

}
