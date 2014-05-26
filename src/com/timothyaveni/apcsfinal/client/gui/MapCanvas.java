package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;

public class MapCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	public final int WIDTH = 1024;
	public final int HEIGHT = WIDTH / 4 * 3;

	// Constructor :D
	public MapCanvas() {
		super();
	}

	public void render(Graphics g) {

		// draws map background on Canvas in frame
		Map m = new Map("E:\\Map.png");
		g.drawImage(m.getPic(), 0, 0, 1024, 768, null);

		g.dispose(); // releases system resources the graphics object is taking
						// up
	}

	@Override
	public void paint(Graphics g) {
		render(g);
	}
}