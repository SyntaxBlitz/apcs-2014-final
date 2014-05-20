package com.timothyaveni.apcsfinal.client.gui;

public abstract class Entity {

	public int height;
	public int width;
	public String name;
	public String fileLocation;
	
	public Entity (String name, String location, int height, int width){
		this.name = name;
		fileLocation = location;
		this.height = height;
		this.width = width;
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
	
}
