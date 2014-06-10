package com.timothyaveni.apcsfinal.client.environmentanimation;

import java.awt.image.BufferedImage;

import com.timothyaveni.apcsfinal.client.Location;

public class Heal extends EnvironmentAnimation {

	public Heal(long startFrame, Location location) {
		super(startFrame, location);
	}

	@Override
	public BufferedImage getImage(long frame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean stillRelevant(long frame) {
		// TODO Auto-generated method stub
		return false;
	}

}
