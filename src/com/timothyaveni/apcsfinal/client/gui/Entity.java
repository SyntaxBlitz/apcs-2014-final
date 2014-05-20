package com.timothyaveni.apcsfinal.client.gui;

public abstract class Entity {

	public int height; //height of the entity in pixels
	public int width; //width of the entity in pixels
	public String name; //name of the entity in terms of type (so Tank's name is Tank)
	public String fileLocation; //stream that represents the files location in the res folder
	
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
	
	public String getFileLocation(){
		return fileLocation;
	}
	
}
