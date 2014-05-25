package com.timothyaveni.apcsfinal.client;

public class Location {
	
	private int x;
	private int y;
	private int direction;
	
	public Location(int x, int y, int direction){
		this.x = x;
		this.y = y;	
		this.direction = direction;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getDirection(){
		return direction;
	}
	
}
