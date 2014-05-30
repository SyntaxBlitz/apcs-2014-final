package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;

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

	public void move(int distance, int direction, String plane){ //Might need to be void?
		if(plane.equals("X"))
		{
			if(direction > 0)
				this.loc.setX(this.loc.getX() + 16);
			else
				this.loc.setX(this.loc.getX() - 16);
		}
		else
		{
			if(direction > 0)
				this.loc.setY(this.loc.getY() + 16);
			else
				this.loc.setY(this.loc.getY() - 16);
		}
		
	}
	
	public void trackPlayer(Location playerLoc){
		int playerX = playerLoc.getX();
		int playerY = playerLoc.getY();
		
		if(playerX - loc.getX() < playerY - loc.getY())
			move((playerX - loc.getX()), (loc.getX() - playerX), "X");
		else
			move((playerY - loc.getY()), (loc.getY() - playerY), "Y");
				
	}


	public Location getLocation(){
		return this.loc;
	}

	public Location getPlayerLocation(){
		return null; //TODO need to get player locations
	}
}
