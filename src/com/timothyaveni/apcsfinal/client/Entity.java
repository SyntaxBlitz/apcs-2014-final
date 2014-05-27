package com.timothyaveni.apcsfinal.client;


public abstract class Entity {


	private int height; // height of the entity in pixels
	private int width; // width of the entity in pixels
	private String name; // name of the entity in terms of type (so Tank's name
						// is Tank)
	private String fileLocation; // stream that represents the files location in
								// the res folder
	private int strength; // strength attribute determines health
	private int intelligence; // intelligence attribute determines mana
	private int speed; // speed determines stamina
	
	private Location loc;


	public Entity(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc) {
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


	public abstract int attack();


	public abstract void move();


}
