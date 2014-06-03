package com.timothyaveni.apcsfinal.client;

public class Location {

	private int x;
	private int y;

	/*
	 * 1 represents North | 2 represents East | 3 represents South | 4
	 * represents West.
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
