package com.timothyaveni.apcsfinal.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.timothyaveni.apcsfinal.networking.EntityTypeID;

public class MapMetadata {

	private ArrayList<EntityInfo> loadedEntityInfo;
	private Location spawnPoint;

	public MapMetadata(File file, Map map) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(new JSONTokener(new FileInputStream(file)));
		} catch (JSONException e) {
			System.out.println("Failed to load map metadata - JSONException");
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to load map metadata - FileNotFoundException");
			System.exit(1);
		}

		this.loadedEntityInfo = new ArrayList<EntityInfo>();

		JSONArray entityArray = jsonObject.getJSONArray("entities");
		for (int i = 0; i < entityArray.length(); i++) {
			JSONObject entityObject = entityArray.getJSONObject(i);
			JSONObject locationObject = entityObject.getJSONObject("location");
			loadedEntityInfo.add(new EntityInfo(EntityTypeID.getType(entityObject.getInt("type")), new Location(
					locationObject.getInt("x"), locationObject.getInt("y"), locationObject.getInt("direction"), map.getWorldSectionId())));
		}

		JSONObject spawnPointLocationObject = jsonObject.getJSONObject("spawnPoint");
		this.spawnPoint = new Location(spawnPointLocationObject.getInt("x"), spawnPointLocationObject.getInt("y"),
				spawnPointLocationObject.getInt("direction"), map.getWorldSectionId());
	}

	public List<EntityInfo> getEntityInfo() {
		return this.loadedEntityInfo;
	}

	/**
	 * 
	 * @return null if the point is not in any exit zone, else the new map name
	 */
	public String getNextMap() {
		return null;
	}

	public Location getSpawnPoint() {
		return this.spawnPoint;
	}
}
