package com.timothyaveni.apcsfinal.client;

public abstract class Entity {

	protected int height; // height of the entity in pixels
	protected int width; // width of the entity in pixels
	protected String name; // name of the entity in terms of type (so Tank's
							// name
							// is Tank)
	protected String fileLocation; // stream that represents the files location
									// in
									// the res folder
	protected int strength; // strength attribute determines health
	protected int intelligence; // intelligence attribute determines mana
	protected int speed; // speed determines stamina

	protected Location loc;

	public Entity(String name, String location, int height, int width,
			int strength, int intelligence, int speed, Location loc) {
		this.name = name;
		fileLocation = location;
		this.height = height;
		this.width = width;
		this.strength = strength;
		this.intelligence = intelligence;
		this.speed = speed;
		this.loc = loc;
	}

	public String getName() {
		return name;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public abstract void move(int distance, Location loc, String plane);

}
