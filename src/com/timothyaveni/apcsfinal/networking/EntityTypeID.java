package com.timothyaveni.apcsfinal.networking;

import com.timothyaveni.apcsfinal.client.Archer;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Healer;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Mage;
import com.timothyaveni.apcsfinal.client.Rogue;
import com.timothyaveni.apcsfinal.client.Tank;
import com.timothyaveni.apcsfinal.server.GoblinEnemy;
import com.timothyaveni.apcsfinal.server.SkeletonEnemy;

public class EntityTypeID {
	public static int getID(EntityType type) {
		switch (type) {
			case TANK:
				return 0;
			case SKELETON_ENEMY:
				return 1;
			case GOBLIN_ENEMY:
				return 2;
			case ROGUE:
				return 3;
			case HEALER:
				return 4;
			case ARCHER:
				return 5;
			case MAGE:
				return 6;
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
			case 2:
				return EntityType.GOBLIN_ENEMY;
			case 3:
				return EntityType.ROGUE;
			case 4:
				return EntityType.HEALER;
			case 5:
				return EntityType.ARCHER;
			case 6:
				return EntityType.MAGE;
			default:
				return null;
		}
	}

	public static Entity constructEntity(EntityType type, int entityId, Location location) {
		switch (type) {
			case TANK:
				return new Tank(entityId, location);
			case SKELETON_ENEMY:
				return new SkeletonEnemy(entityId, location);
			case GOBLIN_ENEMY:
				return new GoblinEnemy(entityId, location);
			case ROGUE:
				return new Rogue(entityId, location);
			case HEALER:
				return new Healer(entityId, location);
			case ARCHER:
				return new Archer(entityId, location);
			case MAGE:
				return new Mage(entityId, location);
		}
		return null;
	}
}
