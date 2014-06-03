package com.timothyaveni.apcsfinal.networking;

public class EntityTypeID {
	public static int getID(EntityType type) {
		switch (type) {
			case TANK:
				return 0;
			case SKELETON_ENEMY:
				return 1;
			default:
				return -1;
		}
	}

	public static EntityType getType(int id) {
		switch (id) {
			case 0:
				return EntityType.TANK;
			case 1:
				return EntityType.SKELETON_ENEMY;
			default:
				return null;
		}
	}
}
