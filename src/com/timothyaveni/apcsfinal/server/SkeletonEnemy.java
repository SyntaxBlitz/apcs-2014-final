package com.timothyaveni.apcsfinal.server;

import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.server.ServerThread;


public class SkeletonEnemy extends Entity implements EnemyAI {
	private int baseDmg;

	public SkeletonEnemy(String name, String location, int height, int width, int strength, int intelligence, int speed, Location loc, int id){
		super(name, location, height, width, strength, intelligence, speed, loc, id);
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

	public int attack(){ //Test
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
	
	
	public EntityDamagePacket trackPlayer(ArrayList<Player> player){ //Tracks player based off the player's location might want all player locations to determine closest?
		ArrayList<Integer> distances = new ArrayList<Integer>();
		Player track;
		int playerDis, temp, playerIndex;
		for(Player p : player)
		{
			distances.add(getDistanceToPlayer(p.getLocation()));
		}
		
		for(int i = 0; i < distances.size(); i++)
		{
			temp = distances.get(i);
			
			if(temp < playerDis)
			{
				playerDis = temp;
				playerIndex = i;
			}
		}
		
		track = player.get(playerIndex);
		
		
		
		
		if(Math.abs(track.getLocation().getX() - loc.getX()) <= 32 || Math.abs(track.getLocation().getY() - loc.getY()) <= 48)
			EntityDamagePacket(ServerThread.getNextPacketId(), track.getId(), baseDmg + speed);
		else if(track.getLocation().getX() - loc.getX() < track.getLocation().getY() - loc.getY())
			move((track.getLocation().getX() - loc.getX()), (loc.getX() - track.getLocation().getX()), "X");
		else if(track.getLocation().getY() - loc.getY() < track.getLocation().getY() - loc.getY())
			move((track.getLocation().getY() - loc.getY()), (loc.getY() - track.getLocation().getY()), "Y");
		
				
	}


	public Location getLocation(){
		return this.loc;
	}

	public Location getPlayerLocation(Location playerLoc){
		return playerLoc; //This might not be needed.
	}
	
	public int getDistanceToPlayer(Location player)
	{
		int playerX = player.getX();
		int playerY = player.getY();
		
		return (int)Math.sqrt(Math.pow(playerX - this.getLocation().getX(), 2) + Math.pow(playerY - this.getLocation().getY(), 2));
	}

}
