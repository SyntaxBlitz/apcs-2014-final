package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.server.EnemyAI;

public class SkeletonEnemy extends Entity implements EnemyAI {
	private int baseDmg;

	SkeletonEnemy(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc){
		super(name, location, height, width, strength, intelligence, speed, loc);
		baseDmg = 30;
		
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

	public int attack(){
		return speed + baseDmg;
		
	}

	public void move(){ //Might need to be void?
		
		
	}
	
	public Location trackPlayer(Location playerLoc){
		int playerX = playerLoc.getX();
		int playerY = playerLoc.getY();
		
		
		
		return null;
		
	}


	public Location getLocation(){
		return null;
	}

	public Location getPlayerLocation(){
		return null;
	}
}
