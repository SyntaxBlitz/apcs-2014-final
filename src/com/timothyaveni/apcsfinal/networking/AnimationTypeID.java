package com.timothyaveni.apcsfinal.networking;

import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.environmentanimation.DamageNumber;
import com.timothyaveni.apcsfinal.client.environmentanimation.EnvironmentAnimation;
import com.timothyaveni.apcsfinal.client.environmentanimation.FireArrows;
import com.timothyaveni.apcsfinal.client.environmentanimation.Heal;
import com.timothyaveni.apcsfinal.client.environmentanimation.LightningBolt;
import com.timothyaveni.apcsfinal.client.environmentanimation.Rage;
import com.timothyaveni.apcsfinal.client.environmentanimation.SwordSpin;

public class AnimationTypeID {
	public static int getID(AnimationType type) {
		switch (type) {
			case DAMAGE_NUMBER:
				return 0;
			case FIRE_ARROWS:
				return 1;
			case LIGHTNING_BOLT:
				return 2;
			case RAGE:
				return 3;
			case HEAL:
				return 4;
			case SWORD_SPIN:
				return 5;
			default:
				return -1;
		}
	}

	public static AnimationType getType(int id) {
		switch (id) {
			case 0:
				return AnimationType.DAMAGE_NUMBER;
			case 1:
				return AnimationType.FIRE_ARROWS;
			case 2:
				return AnimationType.LIGHTNING_BOLT;
			case 3:
				return AnimationType.RAGE;
			case 4:
				return AnimationType.HEAL;
			case 5:
				return AnimationType.SWORD_SPIN;
			default:
				return null;
		}
	}

	public static EnvironmentAnimation constructAnimation(AnimationType type, long frame, Location location, int data) {
		switch (type) {
			case DAMAGE_NUMBER:
				return new DamageNumber(frame, location, data);
			case FIRE_ARROWS:
				return new FireArrows(frame, location);
			case HEAL:
				return new Heal(frame, location);
			case LIGHTNING_BOLT:
				return new LightningBolt(frame, location);
			case RAGE:
				return new Rage(frame, location);
			case SWORD_SPIN:
				return new SwordSpin(frame, location);
		}
		return null;
	}
}
