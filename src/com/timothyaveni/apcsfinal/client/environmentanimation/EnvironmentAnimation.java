package com.timothyaveni.apcsfinal.client.environmentanimation;

import java.awt.image.BufferedImage;

import com.timothyaveni.apcsfinal.client.Location;

public abstract class EnvironmentAnimation {
	
	private long startFrame;
	private Location location;
	
	public EnvironmentAnimation(long startFrame, Location location) {
		this.startFrame = startFrame;
		this.location = location;
	}
	
	protected long getStartFrame() {
		return this.startFrame;
	}
	
	public Location getLocation() {
		return this.location;
	}

	public abstract BufferedImage getImage(long frame);
	
}
