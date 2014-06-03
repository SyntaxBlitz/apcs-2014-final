package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.Entity;

public class MapCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	public final int WIDTH = 1024;
	public final int HEIGHT = WIDTH / 4 * 3;
	private Client c;

	// Constructor :D
	public MapCanvas(Client c) {
		super();
		this.c = c;
	}

	public static void render(Graphics g) {

		// draws map background on Canvas in frame
		Map m = new Map("E:\\Map.png");
		g.drawImage(m.getPic(), 0, 0, 1024, 768, null);
		
		ArrayList<Entity> e = c.getArrayList();
		
		for(int i = 0; i < e.size(); i++){
			g.drawImage(e.get(i).getImage(), 0, 0, e.get(i).getWidth(), e.get(i).getHeight());
		}

		//pull arraylist down off client
		//misc comment so I'm able to commit
		g.dispose(); // releases system resources the graphics object is taking
						// up
	}

	@Override
	public void paint(Graphics g) {
		render(g);
	}
}