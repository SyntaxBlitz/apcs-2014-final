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

	public void move(int distance, int direction, String plane){
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
	
	public EntityDamagePacket trackPlayer(Location playerLoc){ //Tracks player based off the player's location might want all player locations to determine closest?
		int playerX = playerLoc.getX();
		int playerY = playerLoc.getY();
		
		if(Math.abs(playerX - loc.getX()) <= 16 || Math.abs(playerY - loc.getY()) <= 16)
			EntityDamagePacket(attack(), "Byte Array"); //This is probably wrong because the method is void
		else if(playerX - loc.getX() < playerY - loc.getY())
			move((playerX - loc.getX()), (loc.getX() - playerX), "X");
		else if(playerY - loc.getY() < playerY - loc.getY())
			move((playerY - loc.getY()), (loc.getY() - playerY), "Y");
		
				
	}


	public Location getLocation(){
		return this.loc;
	}

	public Location getPlayerLocation(Location playerLoc){
		return playerLoc; //This might not be needed.
	}
}
