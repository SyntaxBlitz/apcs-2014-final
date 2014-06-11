package com.timothyaveni.apcsfinal.networking;

public class WorldSectionID {

	public static String getMapNameFromID(int id) {
		switch (id) {
			case 1:
				return "Map1";
			case 2:
				return "Map2";
			case 3:
				return "Map3";
			default:
				return "";
		}
	}

}
