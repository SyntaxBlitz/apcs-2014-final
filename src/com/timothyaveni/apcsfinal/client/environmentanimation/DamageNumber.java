package com.timothyaveni.apcsfinal.client.environmentanimation;

import java.awt.image.BufferedImage;

import com.timothyaveni.apcsfinal.client.Location;

public class DamageNumber extends EnvironmentAnimation {

	private int damageAmount;
	
	public DamageNumber(long startFrame, Location location, int data) {
		super(startFrame, location);
		this.damageAmount = data;
	}

	@Override
	public BufferedImage getImage(long frame) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
