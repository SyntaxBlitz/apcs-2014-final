package com.timothyaveni.apcsfinal.client.environmentanimation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.timothyaveni.apcsfinal.client.FileReader;
import com.timothyaveni.apcsfinal.client.Location;

public class LightningBolt extends EnvironmentAnimation {

	BufferedImage image;
	
	public LightningBolt(long startFrame, Location location) {
		super(startFrame, location);
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString("MagicSparks.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getImage(long frame) {
		int renderFrame = (int) ((frame - getStartFrame()) / 5);
		return image.getSubimage(renderFrame * getWidth(), 0, getWidth(), getHeight());
	}

	private int getWidth() {
		return 288;
	}
	
	private int getHeight() {
		return 288;
	}
	
	@Override
	public boolean stillRelevant(long frame) {
		return frame - getStartFrame() < 25;
	}

}
