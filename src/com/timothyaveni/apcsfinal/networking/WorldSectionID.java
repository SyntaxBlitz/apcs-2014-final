package com.timothyaveni.apcsfinal.networking;

public class WorldSectionID {

	public static String getMapNameFromID(int id) {
		switch (id) {
			case 1:
				return "Map1";
			default:
				return "";
		}
	}

}
