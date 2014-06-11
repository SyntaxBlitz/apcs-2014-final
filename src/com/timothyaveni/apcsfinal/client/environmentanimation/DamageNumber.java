package com.timothyaveni.apcsfinal.client.environmentanimation;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.timothyaveni.apcsfinal.client.Location;

public class DamageNumber extends EnvironmentAnimation {

	private int damageAmount;
	private Font font = new Font("Arial", Font.BOLD, 20);

	public DamageNumber(long startFrame, Location location, int data) {
		super(startFrame, location);
		this.damageAmount = data;
	}

	@Override
	public BufferedImage getImage(long frame) {
		BufferedImage image = new BufferedImage(128, 32, BufferedImage.TYPE_INT_ARGB);

		int yVal = (int) (32 - (frame - getStartFrame()));
		int alpha = yVal * 255 / 32;

		Graphics g = image.getGraphics();

		String renderString;
		if (damageAmount > 0) {
			renderString = "" + damageAmount;
			g.setColor(new Color(255, 64, 64, alpha));
		} else {
			renderString = "" + (-damageAmount);
			g.setColor(new Color(64, 255, 64, alpha));
		}

		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();

		g.drawString(renderString, 64 - metrics.charsWidth(renderString.toCharArray(), 0, renderString.length()) / 2,
				yVal);

		return image;
	}

	@Override
	public boolean stillRelevant(long frame) {
		return (frame - getStartFrame() < 32);
	}

}
