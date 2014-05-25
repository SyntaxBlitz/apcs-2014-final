package com.timothyaveni.apcsfinal.client;

public abstract class Entity {
	
	public int height; //height of the entity in pixels
	public int width; //width of the entity in pixels
	public String name; //name of the entity in terms of type (so Tank's name is Tank)
	public String fileLocation; //stream that represents the files location in the res folder
	public int strength; //strength attribute determines health
	public int intelligence; //intelligence attribute determines mana
	public int speed; //speed determines stamina
	
	public Entity (String name, String location, int height, int width,
			int strength, int intelligence, int speed){
		this.name = name;
		fileLocation = location;
		this.height = height;
		this.width = width;
		this.strength = strength;
		this.intelligence = intelligence;
		this.speed = speed;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public String getFileLocation(){
		return fileLocation;
	}
	
	public abstract int attack();
	
	public abstract Location move();
	
	
}
