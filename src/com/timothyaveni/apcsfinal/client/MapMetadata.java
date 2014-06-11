package com.timothyaveni.apcsfinal.client;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.timothyaveni.apcsfinal.networking.EntityTypeID;

public class MapMetadata {

	private class ExitArea {
		Rectangle rectangle;
		int nextMap;
	}

	private BufferedImage collisionMap;

	private ArrayList<EntityInfo> loadedEntityInfo;
	private Location spawnPoint;
	private ArrayList<ExitArea> exitAreas;

	public MapMetadata(String mapName, int worldSectionId) throws IOException {
		collisionMap = ImageIO.read(FileReader.getFileFromResourceString(mapName + "_collision.png"));

		File jsonFile = FileReader.getFileFromResourceString(mapName + "_metadata.json");
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(new JSONTokener(new FileInputStream(jsonFile)));
		} catch (JSONException e) {
			System.out.println("Failed to load map metadata - JSONException");
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to load map metadata - FileNotFoundException");
			System.exit(1);
		}

		this.loadedEntityInfo = new ArrayList<EntityInfo>();
		this.exitAreas = new ArrayList<ExitArea>();

		JSONArray entityArray = jsonObject.getJSONArray("entities");
		for (int i = 0; i < entityArray.length(); i++) {
			JSONObject entityObject = entityArray.getJSONObject(i);
			JSONObject locationObject = entityObject.getJSONObject("location");
			loadedEntityInfo.add(new EntityInfo(EntityTypeID.getType(entityObject.getInt("type")), new Location(
					locationObject.getInt("x"), locationObject.getInt("y"), locationObject.getInt("direction"),
					worldSectionId)));
		}

		JSONObject spawnPointLocationObject = jsonObject.getJSONObject("spawnPoint");
		this.spawnPoint = new Location(spawnPointLocationObject.getInt("x"), spawnPointLocationObject.getInt("y"),
				spawnPointLocationObject.getInt("direction"), worldSectionId);

		JSONArray exitAreaArray = jsonObject.getJSONArray("exitAreas");
		for (int i = 0; i < exitAreaArray.length(); i++) {
			JSONObject exitAreaObject = exitAreaArray.getJSONObject(i);
			ExitArea area = new ExitArea();
			area.rectangle = new Rectangle(exitAreaObject.getInt("x"), exitAreaObject.getInt("y"),
					exitAreaObject.getInt("width"), exitAreaObject.getInt("height"));
			area.nextMap = exitAreaObject.getInt("newMapId");
			exitAreas.add(area);
		}
	}

	public List<EntityInfo> getEntityInfo() {
		return this.loadedEntityInfo;
	}

	/**
	 * 
	 * @return -1 if the point is not in any exit zone, else the new map world section id
	 */
	public int getNextMap(int x, int y) {
		for (ExitArea area : exitAreas) {
			if (area.rectangle.contains(x, y))
				return area.nextMap;
		}
		return -1;
	}

	public Location getSpawnPoint() {
		return this.spawnPoint;
	}

	public boolean isPointValid(int x, int y) {
		return (collisionMap.getRGB(x, y) & 0xFFFFFF) == 0xFFFFFF;
	}
}
