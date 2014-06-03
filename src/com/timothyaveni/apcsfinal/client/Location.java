package com.timothyaveni.apcsfinal.client;

public class Location {

	private int x;
	private int y;

	/*
	 * 1 represents South | 2 represents West | 3 represents East | 4 represents
	 * North.
	 */
	private int direction;

	public Location(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int d) {
		direction = d;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getDistanceTo(Location other) {
		return Math.sqrt((other.getX() - this.getX()) * (other.getX() - this.getX()) + (other.getY() - this.getY())
				* (other.getY() - this.getY()));
	}
}
